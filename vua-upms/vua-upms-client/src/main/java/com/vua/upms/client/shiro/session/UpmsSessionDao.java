package com.vua.upms.client.shiro.session;

import com.vua.common.util.RedisUtil;
import com.vua.upms.client.util.SerializableUtil;
import com.vua.upms.common.constant.UpmsConstant;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

/**
 * Created by liunian on 6/7/17.
 */
public class UpmsSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(UpmsSessionDao.class);
    // session key
    private final static String VUA_UPMS_SHIRO_SESSION_ID = "vua-upms-shiro-serrsion-id";
    // global session key
    private final static String VUA_UPMS_SERVER_SESSION_ID = "vua-upms-server-session-id";
    // global session list key 会话Id列表
    private final static String VUA_UPMS_SERVER_SESSION_IDS = "vua-upms-server-session-ids";
    // code key
    private final static String VUA_UPMS_SERVER_CODE = "vua-upms-server-code";
    // local session key
    private final static String VUA_UPMS_CLIENT_SESSION_ID = "vua-upms-client-session-id";
    // 单点同一个code所有局部会话key
    private final static String VUA_UPMS_CLIENT_SESSION_IDS = "vua-upms-client-session-ids";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        //set sessionId to SimpleSession
        assignSessionId(session, sessionId);
        RedisUtil.set(VUA_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int)session.getTimeout() / 1000);
        _log.debug("doCreate >>>> sessionId ={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = RedisUtil.get(VUA_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        _log.debug("doReadSession >>>> sessionid={}", sessionId);
        //返回反序列化的session对象
        return SerializableUtil.deserialize(session);
    }

    @Override
    protected void doUpdate(Session session) {
        // session 过期／停止不需要更新
        if (session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return;
        }
        // update session
        UpmsSession upmsSession = (UpmsSession)session;
        UpmsSession tempUpmsSession = (UpmsSession)doReadSession(session.getId());
        if (null != tempUpmsSession) {
            upmsSession.setStatus(tempUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", tempUpmsSession.getAttribute("FORCE_LOGOUT"));
        }
        RedisUtil.set(VUA_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int)session.getTimeout() / 1000);
        _log.debug("doUpdate >>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        // session 类型
        String upmsType = ObjectUtils.toString(session.getAttribute(UpmsConstant.UPMS_TYPE));
        if ("client".equals(upmsType)) {
            //删除局部会话和同一code注册的局部会话
            String code = RedisUtil.get(VUA_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            Jedis jedis = RedisUtil.getJedis();
            jedis.del(VUA_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            //移除集合中的值，客户端sessionId～
            jedis.srem(VUA_UPMS_CLIENT_SESSION_IDS + "_" + code, sessionId);
            jedis.close();
        }
        if ("server".equals(upmsType)) {
            //当前全局会话code
            String code = RedisUtil.get(VUA_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            //清除全局会话
            RedisUtil.remove(VUA_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            //清除code校验值
            RedisUtil.remove(VUA_UPMS_SERVER_CODE + "_" + code);
            //清除所用局部会话
            Jedis jedis = RedisUtil.getJedis();
            Set<String> clientSessionIds = jedis.smembers(VUA_UPMS_CLIENT_SESSION_ID + "_" + code);
            for (String clientSessionId : clientSessionIds) {
                //删除对应的客户端sessionId
                jedis.del(VUA_UPMS_CLIENT_SESSION_ID + "_" + clientSessionId);
                jedis.srem(VUA_UPMS_CLIENT_SESSION_IDS + "_" + code,clientSessionId);
            }
            //scard 获取存储在集合中的元素数量
            _log.debug("当前code={}，对应的注册系统个数：{}个", code, jedis.scard(VUA_UPMS_CLIENT_SESSION_IDS + "_" + code));
            jedis.close();
            //update session list  count表示移除几个元素
            RedisUtil.lrem(VUA_UPMS_SERVER_SESSION_IDS, 1, sessionId);
        }
        //remove session
        RedisUtil.remove(VUA_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        _log.debug("doDelete >>>>> sessionId={}", sessionId);
    }

    //获取会话列表
    public Map getActiveSessions(int offset, int limit) {
        Map sessions = new HashMap();
        Jedis jedis = RedisUtil.getJedis();
        // 获取在线会话总数
        long total = jedis.llen(VUA_UPMS_SERVER_SESSION_IDS);
        // 获取页会话数据
        List<String> ids = jedis.lrange(VUA_UPMS_SERVER_SESSION_IDS, offset, (offset + limit - 1));
        List<Session> sessionLists = new ArrayList<>();
        for (String id : ids) {
            String session = RedisUtil.get(VUA_UPMS_SHIRO_SESSION_ID + "_" + id);
            //filter timeout session
            if (null == session) {
                RedisUtil.lrem(VUA_UPMS_SERVER_SESSION_IDS, 1 ,id);
                total = total - 1;
                continue;
            }
            sessionLists.add(SerializableUtil.deserialize(session));
        }
        jedis.close();
        sessions.put("total", total);
        sessions.put("rows", sessionLists);

        return sessions;
    }

    //强制退出
    public int forceout(String ids) {
        String[] sessionIds = ids.split(",");
        for (String sessionId : sessionIds) {
            //会话增加强制退出属性标识，当此会话访问系统时，判断有该标识，则退出登录
            String session = RedisUtil.get(VUA_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
            UpmsSession upmsSession = (UpmsSession)SerializableUtil.deserialize(session);
            upmsSession.setStatus(UpmsSession.OnlineStatus.force_logout);
            upmsSession.setAttribute("FORCE_LOGOUT", "FORCE_LOGOUT");
            RedisUtil.set(VUA_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(upmsSession), (int)upmsSession.getTimeout() / 1000);
        }
        return sessionIds.length;
    }

    //更新在线状态
    public void updateStatus(Serializable sessionId, UpmsSession.OnlineStatus onlineStatus) {
        UpmsSession session = (UpmsSession)doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        RedisUtil.set(VUA_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int)session.getTimeout() / 1000);
    }
}