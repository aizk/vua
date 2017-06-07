package com.vua.upms.client.shiro.session;

import com.vua.common.util.RedisUtil;
import com.vua.upms.client.util.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by liunian on 6/7/17.
 */
public class UpmsSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(UpmsSessionDao.class);
    // session key
    private final static String VUA_UPMS_SHIRO_SESSION_ID = "vua-upms-shiro-serrsion-id";
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
            upmsSession.setAttribute("FORCE_LOGOUt", tempUpmsSession.getAttribute("FORCE_LOGOUT"));
        }
        RedisUtil.set(VUA_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int)session.getTimeout() / 1000);
        _log.debug("doUpdate >>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {

    }
}
