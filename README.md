# 项目介绍
基于Spring + SpringMVC + Mybatis 系统架构，实现集中式的单点登录与权限管理(Shiro进行认证和授权)，dubbo分布式服务构建，方便的为各类子系统提供登陆与授权服务。
# 组织结构
```lua
vua
├── vua-common ── SSM框架公共管理模块(Maven通用依赖引入)
├── vua-upms ── 用户权限管理系统
|    ├── vua-upms-client upms依赖包，提供单点登录、授权、统一会话管理
|    ├── vua-upms-dao mybatis-generator自动生成模块
|    ├── vua-upms-rpc-api rpc接口
|    ├── vua-upms-rpc-service rpc服务启动、提供实现
|    ├── vua-upms-server Web管理
```
# 技术选型
## 后端技术
技术 | 名称 | 官网
----|------|----
Spring Framework | 容器  | [http://projects.spring.io/spring-framework/](http://projects.spring.io/spring-framework/)
SpringMVC | MVC框架  | [http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc)
Apache Shiro | 安全框架  | [http://shiro.apache.org/](http://shiro.apache.org/)
Spring session | 分布式Session管理  | [http://projects.spring.io/spring-session/](http://projects.spring.io/spring-session/)
MyBatis | ORM框架  | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
MyBatis Generator | 代码生成  | [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
PageHelper | MyBatis物理分页插件  | [http://git.oschina.net/free/Mybatis_PageHelper](http://git.oschina.net/free/Mybatis_PageHelper)
Druid | 数据库连接池  | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
ZooKeeper | 分布式协调服务  | [http://zookeeper.apache.org/](http://zookeeper.apache.org/)
Dubbo | 分布式服务框架  | [http://dubbo.io/](http://dubbo.io/)
Redis | 分布式缓存数据库  | [https://redis.io/](https://redis.io/)
ehcache | 进程内缓存框架  | [http://www.ehcache.org/](http://www.ehcache.org/)
Log4J | 日志组件  | [http://logging.apache.org/log4j/1.2/](http://logging.apache.org/log4j/1.2/)
Jenkins | 持续集成工具  | [https://jenkins.io/index.html](https://jenkins.io/index.html)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/


add a line.)
