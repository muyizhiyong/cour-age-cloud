# courage-cloud
山腰上人太多了，记得去山顶上看看！

#主题
用于载入springcloud相关部件

#说明
- 该项目采用maven构建

#依赖
已完成：
- Spring Cloud Version : Hoxton.SR5
- Spring Cloud Alibaba Version : 2.2.0.RELEASE
- Spring Boot Version : 2.3.0.RELEASE
- Nacos Version : 1.4.1
- Sentinel Version: 1.8.0
- Dubbo Version : 2.7.8

未注入：
- RocketMQ Version : 4.4.0
- Seata Version : 1.3.0

进度说明
* 1.auth模块，登陆相关操作，回调oauth模块进行oauth模块鉴权,jWT存储在redis中；[需改造统一]
* 2.oauth模块，用户信息&权限信息加载至redis，oauth2认证；[启动时加载至缓存，需考虑信息变化同步]
* 3.gate模块，以nacos为注册中心，负责路由分发与Token鉴权；
* 4.user模块，数据库读写分离，内部service服务通过dubbo完成（注册中心采用zookeeper）；

待完成事项
* 1.websocket推送
* 2.定时调度模块
* 3.MQ通讯模块
