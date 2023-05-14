/*
 Navicat Premium Data Transfer

 Source Server         : YCloud-ali
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : xiaosm-yyy.mysql.rds.aliyuncs.com:3306
 Source Schema         : ycloud

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 01/04/2023 20:33:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ban
-- ----------------------------
DROP TABLE IF EXISTS `ban`;
CREATE TABLE `ban`  (
  `user_id` int NOT NULL,
  `role_id` int NULL DEFAULT NULL,
  `menu_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `ban_role_id_fk`(`role_id`) USING BTREE,
  INDEX `ban_menu_id_fk`(`menu_id`) USING BTREE,
  CONSTRAINT `ban_menu_id_fk` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ban_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ban_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ban
-- ----------------------------

-- ----------------------------
-- Table structure for bucket
-- ----------------------------
DROP TABLE IF EXISTS `bucket`;
CREATE TABLE `bucket`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `zh_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bucket
-- ----------------------------
INSERT INTO `bucket` VALUES (1, 'local', '本地仓库', NULL, 1, 'LOCAL', NULL, NULL);
INSERT INTO `bucket` VALUES (2, 'local', '本地仓库', NULL, 2, 'LOCAL', NULL, NULL);
INSERT INTO `bucket` VALUES (3, 'local', '本地仓库', NULL, 3, 'LOCAL', NULL, NULL);

-- ----------------------------
-- Table structure for chunk
-- ----------------------------
DROP TABLE IF EXISTS `chunk`;
CREATE TABLE `chunk`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '当前分块hash',
  `file_hash` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '完整文件hash',
  `order` int NOT NULL COMMENT '分块顺序',
  `total` int NOT NULL DEFAULT 0 COMMENT '分块总数',
  `size` bigint NULL DEFAULT NULL COMMENT '分块大小',
  `user_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `type` tinyint NOT NULL COMMENT '1下载，2上传',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chunk
-- ----------------------------
INSERT INTO `chunk` VALUES (94, '6663f9661289b6c838ad4b960db2ccfd', '654865571b0876b35fb7002a95508f6e', 1, 2, 31457280, NULL, '2023-01-20 16:49:19', 2);
INSERT INTO `chunk` VALUES (95, '3f1a06b4cefe6aebd20802ebd8badc3f', '654865571b0876b35fb7002a95508f6e', 2, 2, 58108416, NULL, '2023-01-20 16:49:25', 2);
INSERT INTO `chunk` VALUES (96, 'e2dfb7610e71442c2dc1221264c29121', 'ccad9cb82ea3e7a4145f3c485914aa73', 1, 5, 31457280, NULL, '2023-02-05 19:55:02', 2);
INSERT INTO `chunk` VALUES (97, '4744109674e637654a091f34209a4e54', 'ccad9cb82ea3e7a4145f3c485914aa73', 2, 5, 31457280, NULL, '2023-02-05 19:55:07', 2);
INSERT INTO `chunk` VALUES (98, '56a32ddb34ec0069355effa51489c1c4', 'ccad9cb82ea3e7a4145f3c485914aa73', 3, 5, 31457280, NULL, '2023-02-05 19:56:22', 2);
INSERT INTO `chunk` VALUES (99, '138216196abdac5d86f9278ce05b833d', 'ccad9cb82ea3e7a4145f3c485914aa73', 4, 5, 31457280, NULL, '2023-02-05 19:56:34', 2);
INSERT INTO `chunk` VALUES (100, '5fbcf55b657cd8cbcc3305a8a0d2a41c', 'ccad9cb82ea3e7a4145f3c485914aa73', 5, 5, 39125113, NULL, '2023-02-05 19:58:03', 2);
INSERT INTO `chunk` VALUES (101, '382c940e8049518815c918285d5d32ce', '2db1be923f2b51077e276147dde574d5', 2, 3, 31457280, NULL, '2023-02-09 22:06:27', 2);
INSERT INTO `chunk` VALUES (102, '56e65878f9b30c1d054c94664e49a83a', '2db1be923f2b51077e276147dde574d5', 1, 3, 31457280, NULL, '2023-02-09 22:06:30', 2);
INSERT INTO `chunk` VALUES (103, '5dced891a16b9445da34ce613b53a322', '2db1be923f2b51077e276147dde574d5', 3, 3, 39073688, NULL, '2023-02-09 22:08:02', 2);
INSERT INTO `chunk` VALUES (104, 'b437c8d3336ececdbf2f7fb675be78ef', '37bf36a944b1784b4f41290a9ea41c3e', 2, 3, 31457280, NULL, '2023-03-24 12:45:53', 2);
INSERT INTO `chunk` VALUES (105, '417531490e9ad4294dadba71a3c67366', '37bf36a944b1784b4f41290a9ea41c3e', 1, 3, 31457280, NULL, '2023-03-24 12:45:55', 2);
INSERT INTO `chunk` VALUES (106, '8e5fc5f6a1e9fb9dff3bc1544db0f05d', '37bf36a944b1784b4f41290a9ea41c3e', 3, 3, 50378658, NULL, '2023-03-24 12:46:11', 2);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志标题',
  `error` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志内容',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志类型',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名称',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `time` int NULL DEFAULT NULL,
  `ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `log_user_Id_fk`(`user_id`) USING BTREE,
  CONSTRAINT `log_user_Id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21831 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (21829, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 4, '0:0:0:0:0:0:0:1', NULL);
INSERT INTO `log` VALUES (21830, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 4, '0:0:0:0:0:0:0:1', NULL);
INSERT INTO `log` VALUES (21831, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 4, '127.0.0.1', '2023-04-01 16:31:14');
INSERT INTO `log` VALUES (21832, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 16:36:06');
INSERT INTO `log` VALUES (21833, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 16:37:31');
INSERT INTO `log` VALUES (21834, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 16:39:20');
INSERT INTO `log` VALUES (21835, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 16:54:12');
INSERT INTO `log` VALUES (21836, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 4, '127.0.0.1', '2023-04-01 16:56:21');
INSERT INTO `log` VALUES (21837, '\r\n### Error querying database.', 'org.springframework.jdbc.BadSqlGrammarException: \r\n### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'ava\' in \'where clause\'\r\n### The error may exist in cn/xiaosm/cloud/core/mapper/ResourceMapper.java (best guess)\r\n### The error may involve cn.xiaosm.cloud.core.mapper.ResourceMapper.selectAllByName-Inline\r\n### The error occurred while setting parameters\r\n### SQL: SELECT * FROM resource WHERE `name` = concat(\'%\', ava, \'%\') AND user_id = ? AND bucket_id = 2\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'ava\' in \'where clause\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'ava\' in \'where clause\'\r\n	at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:239)\r\n	at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:70)\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:91)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:441)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy119.selectList(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:224)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.executeForMany(MybatisMapperMethod.java:166)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.execute(MybatisMapperMethod.java:77)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$PlainMethodInvoker.invoke(MybatisMapperProxy.java:148)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at jdk.proxy3/jdk.proxy3.$Proxy168.selectAllByName(Unknown Source)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\r\n	at jdk.proxy3/jdk.proxy3.$Proxy169.selectAllByName(Unknown Source)\r\n	at cn.xiaosm.cloud.core.service.impl.ResourceServiceImpl.search(ResourceServiceImpl.java:651)\r\n	at cn.xiaosm.cloud.core.service.impl.ResourceServiceImpl$$FastClassBySpringCGLIB$$ec23be96.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\r\n	at cn.xiaosm.cloud.core.service.impl.ResourceServiceImpl$$EnhancerBySpringCGLIB$$edb82926.search(<generated>)\r\n	at cn.xiaosm.cloud.core.controller.ResourceController.search(ResourceController.java:45)\r\n	at cn.xiaosm.cloud.core.controller.ResourceController$$FastClassBySpringCGLIB$$9b49f98.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:783)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)\r\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:698)\r\n	at cn.xiaosm.cloud.core.controller.ResourceController$$EnhancerBySpringCGLIB$$460120b5.search(<generated>)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:124)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:327)\r\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115)\r\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:122)\r\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:116)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126)\r\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:109)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:219)\r\n	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:213)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at cn.xiaosm.cloud.security.DefaultJWTAuthenticationFilter.doFilterInternal(DefaultJWTAuthenticationFilter.java:67)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at cn.xiaosm.cloud.security.DefaultSessionAuthenticationFilter.doFilterInternal(DefaultSessionAuthenticationFilter.java:53)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103)\r\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)\r\n	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:110)\r\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:80)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:211)\r\n	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:183)\r\n	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:354)\r\n	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:267)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:96)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:197)\r\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\r\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:540)\r\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:135)\r\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\r\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:359)\r\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:399)\r\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\r\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:889)\r\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1735)\r\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n	at java.base/java.lang.Thread.run(Thread.java:833)\r\nCaused by: java.sql.SQLSyntaxErrorException: Unknown column \'ava\' in \'where clause\'\r\n	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)\r\n	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)\r\n	at com.mysql.cj.jdbc.ClientPreparedStatement.executeInternal(ClientPreparedStatement.java:953)\r\n	at com.mysql.cj.jdbc.ClientPreparedStatement.execute(ClientPreparedStatement.java:371)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3461)\r\n	at com.alibaba.druid.wall.WallFilter.preparedStatement_execute(WallFilter.java:626)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3459)\r\n	at com.alibaba.druid.filter.FilterEventAdapter.preparedStatement_execute(FilterEventAdapter.java:440)\r\n	at com.alibaba.druid.filter.FilterChainImpl.preparedStatement_execute(FilterChainImpl.java:3459)\r\n	at com.alibaba.druid.proxy.jdbc.PreparedStatementProxyImpl.execute(PreparedStatementProxyImpl.java:167)\r\n	at com.alibaba.druid.pool.DruidPooledPreparedStatement.execute(DruidPooledPreparedStatement.java:497)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.apache.ibatis.logging.jdbc.PreparedStatementLogger.invoke(PreparedStatementLogger.java:59)\r\n	at jdk.proxy4/jdk.proxy4.$Proxy238.execute(Unknown Source)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:64)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:79)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:64)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy236.query(Unknown Source)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:63)\r\n	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:325)\r\n	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)\r\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)\r\n	at com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor.intercept(MybatisPlusInterceptor.java:81)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:62)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy235.query(Unknown Source)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:151)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:145)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:140)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:427)\r\n	... 149 more\r\n', NULL, 'ERROR', NULL, NULL, NULL, '127.0.0.1', NULL);
INSERT INTO `log` VALUES (21838, '\r\n### Error querying database.', 'org.springframework.jdbc.UncategorizedSQLException: \r\n### Error querying database.  Cause: java.sql.SQLException: sql injection violation, syntax error: syntax error, error in :\'e` = concat(\'%\', ?,\'%\'), \'%\') AND u\', expect COMMA, actual COMMA pos 57, line 1, column 57, token COMMA : SELECT * FROM resource WHERE `name` = concat(\'%\', ?,\'%\'), \'%\') AND user_id = ? AND bucket_id = 2\r\n### The error may exist in cn/xiaosm/cloud/core/mapper/ResourceMapper.java (best guess)\r\n### The error may involve cn.xiaosm.cloud.core.mapper.ResourceMapper.selectAllByName\r\n### The error occurred while executing a query\r\n### SQL: SELECT * FROM resource WHERE `name` = concat(\'%\', ?,\'%\'), \'%\') AND user_id = ? AND bucket_id = 2\r\n### Cause: java.sql.SQLException: sql injection violation, syntax error: syntax error, error in :\'e` = concat(\'%\', ?,\'%\'), \'%\') AND u\', expect COMMA, actual COMMA pos 57, line 1, column 57, token COMMA : SELECT * FROM resource WHERE `name` = concat(\'%\', ?,\'%\'), \'%\') AND user_id = ? AND bucket_id = 2\n; uncategorized SQLException; SQL state [null]; error code [0]; sql injection violation, syntax error: syntax error, error in :\'e` = concat(\'%\', ?,\'%\'), \'%\') AND u\', expect COMMA, actual COMMA pos 57, line 1, column 57, token COMMA : SELECT * FROM resource WHERE `name` = concat(\'%\', ?,\'%\'), \'%\') AND user_id = ? AND bucket_id = 2; nested exception is java.sql.SQLException: sql injection violation, syntax error: syntax error, error in :\'e` = concat(\'%\', ?,\'%\'), \'%\') AND u\', expect COMMA, actual COMMA pos 57, line 1, column 57, token COMMA : SELECT * FROM resource WHERE `name` = concat(\'%\', ?,\'%\'), \'%\') AND user_id = ? AND bucket_id = 2\r\n	at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:92)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:441)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy119.selectList(Unknown Source)\r\n	at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:224)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.executeForMany(MybatisMapperMethod.java:166)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperMethod.execute(MybatisMapperMethod.java:77)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy$PlainMethodInvoker.invoke(MybatisMapperProxy.java:148)\r\n	at com.baomidou.mybatisplus.core.override.MybatisMapperProxy.invoke(MybatisMapperProxy.java:89)\r\n	at jdk.proxy3/jdk.proxy3.$Proxy168.selectAllByName(Unknown Source)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\r\n	at jdk.proxy3/jdk.proxy3.$Proxy169.selectAllByName(Unknown Source)\r\n	at cn.xiaosm.cloud.core.service.impl.ResourceServiceImpl.search(ResourceServiceImpl.java:651)\r\n	at cn.xiaosm.cloud.core.service.impl.ResourceServiceImpl$$FastClassBySpringCGLIB$$ec23be96.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\r\n	at cn.xiaosm.cloud.core.service.impl.ResourceServiceImpl$$EnhancerBySpringCGLIB$$63c2ab8f.search(<generated>)\r\n	at cn.xiaosm.cloud.core.controller.ResourceController.search(ResourceController.java:45)\r\n	at cn.xiaosm.cloud.core.controller.ResourceController$$FastClassBySpringCGLIB$$9b49f98.invoke(<generated>)\r\n	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:783)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)\r\n	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)\r\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\r\n	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)\r\n	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:698)\r\n	at cn.xiaosm.cloud.core.controller.ResourceController$$EnhancerBySpringCGLIB$$910be32b.search(<generated>)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205)\r\n	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150)\r\n	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895)\r\n	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)\r\n	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\r\n	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1067)\r\n	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:963)\r\n	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\r\n	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)\r\n	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\r\n	at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:227)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:124)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:327)\r\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115)\r\n	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:122)\r\n	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:116)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126)\r\n	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:109)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:219)\r\n	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:213)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:111)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at cn.xiaosm.cloud.security.DefaultJWTAuthenticationFilter.doFilterInternal(DefaultJWTAuthenticationFilter.java:67)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at cn.xiaosm.cloud.security.DefaultSessionAuthenticationFilter.doFilterInternal(DefaultSessionAuthenticationFilter.java:53)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103)\r\n	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)\r\n	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:110)\r\n	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:80)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)\r\n	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:211)\r\n	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:183)\r\n	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:354)\r\n	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:267)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:96)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117)\r\n	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:189)\r\n	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:162)\r\n	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:197)\r\n	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)\r\n	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:540)\r\n	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:135)\r\n	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)\r\n	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:359)\r\n	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:399)\r\n	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\r\n	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:889)\r\n	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1735)\r\n	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n	at java.base/java.lang.Thread.run(Thread.java:833)\r\nCaused by: java.sql.SQLException: sql injection violation, syntax error: syntax error, error in :\'e` = concat(\'%\', ?,\'%\'), \'%\') AND u\', expect COMMA, actual COMMA pos 57, line 1, column 57, token COMMA : SELECT * FROM resource WHERE `name` = concat(\'%\', ?,\'%\'), \'%\') AND user_id = ? AND bucket_id = 2\r\n	at com.alibaba.druid.wall.WallFilter.checkInternal(WallFilter.java:805)\r\n	at com.alibaba.druid.wall.WallFilter.connection_prepareStatement(WallFilter.java:258)\r\n	at com.alibaba.druid.filter.FilterChainImpl.connection_prepareStatement(FilterChainImpl.java:568)\r\n	at com.alibaba.druid.filter.FilterAdapter.connection_prepareStatement(FilterAdapter.java:930)\r\n	at com.alibaba.druid.filter.FilterEventAdapter.connection_prepareStatement(FilterEventAdapter.java:122)\r\n	at com.alibaba.druid.filter.FilterChainImpl.connection_prepareStatement(FilterChainImpl.java:568)\r\n	at com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl.prepareStatement(ConnectionProxyImpl.java:341)\r\n	at com.alibaba.druid.pool.DruidPooledConnection.prepareStatement(DruidPooledConnection.java:350)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.apache.ibatis.logging.jdbc.ConnectionLogger.invoke(ConnectionLogger.java:55)\r\n	at jdk.proxy4/jdk.proxy4.$Proxy237.prepareStatement(Unknown Source)\r\n	at org.apache.ibatis.executor.statement.PreparedStatementHandler.instantiateStatement(PreparedStatementHandler.java:86)\r\n	at org.apache.ibatis.executor.statement.BaseStatementHandler.prepare(BaseStatementHandler.java:88)\r\n	at org.apache.ibatis.executor.statement.RoutingStatementHandler.prepare(RoutingStatementHandler.java:59)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.apache.ibatis.plugin.Invocation.proceed(Invocation.java:49)\r\n	at com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor.intercept(MybatisPlusInterceptor.java:106)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:62)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy236.prepare(Unknown Source)\r\n	at org.apache.ibatis.executor.SimpleExecutor.prepareStatement(SimpleExecutor.java:87)\r\n	at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:62)\r\n	at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:325)\r\n	at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:156)\r\n	at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:109)\r\n	at com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor.intercept(MybatisPlusInterceptor.java:81)\r\n	at org.apache.ibatis.plugin.Plugin.invoke(Plugin.java:62)\r\n	at jdk.proxy2/jdk.proxy2.$Proxy235.query(Unknown Source)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:151)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:145)\r\n	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:140)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\r\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)\r\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\r\n	at java.base/java.lang.reflect.Method.invoke(Method.java:568)\r\n	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:427)\r\n	... 149 more\r\nCaused by: com.alibaba.druid.sql.parser.ParserException: syntax error, error in :\'e` = concat(\'%\', ?,\'%\'), \'%\') AND u\', expect COMMA, actual COMMA pos 57, line 1, column 57, token COMMA\r\n	at com.alibaba.druid.sql.parser.SQLParser.printError(SQLParser.java:344)\r\n	at com.alibaba.druid.sql.parser.SQLStatementParser.parseStatementList(SQLStatementParser.java:532)\r\n	at com.alibaba.druid.sql.parser.SQLStatementParser.parseStatementList(SQLStatementParser.java:182)\r\n	at com.alibaba.druid.wall.WallProvider.checkInternal(WallProvider.java:624)\r\n	at com.alibaba.druid.wall.WallProvider.check(WallProvider.java:578)\r\n	at com.alibaba.druid.wall.WallFilter.checkInternal(WallFilter.java:792)\r\n	... 189 more\r\n', NULL, 'ERROR', NULL, NULL, NULL, '127.0.0.1', NULL);
INSERT INTO `log` VALUES (21839, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 10, '127.0.0.1', '2023-04-01 17:09:04');
INSERT INTO `log` VALUES (21840, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:15:05');
INSERT INTO `log` VALUES (21841, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:17:18');
INSERT INTO `log` VALUES (21842, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:17:25');
INSERT INTO `log` VALUES (21843, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 1, '127.0.0.1', '2023-04-01 17:21:22');
INSERT INTO `log` VALUES (21844, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:23:59');
INSERT INTO `log` VALUES (21845, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:30:53');
INSERT INTO `log` VALUES (21846, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:33:27');
INSERT INTO `log` VALUES (21847, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:35:00');
INSERT INTO `log` VALUES (21848, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 1, '127.0.0.1', '2023-04-01 17:39:29');
INSERT INTO `log` VALUES (21849, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:40:15');
INSERT INTO `log` VALUES (21850, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:40:29');
INSERT INTO `log` VALUES (21851, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:40:39');
INSERT INTO `log` VALUES (21852, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:41:02');
INSERT INTO `log` VALUES (21853, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:51:57');
INSERT INTO `log` VALUES (21854, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 1, '127.0.0.1', '2023-04-01 17:53:40');
INSERT INTO `log` VALUES (21855, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:54:08');
INSERT INTO `log` VALUES (21856, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:54:21');
INSERT INTO `log` VALUES (21857, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:55:55');
INSERT INTO `log` VALUES (21858, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:56:47');
INSERT INTO `log` VALUES (21859, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:57:15');
INSERT INTO `log` VALUES (21860, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 17:58:14');
INSERT INTO `log` VALUES (21861, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:01:36');
INSERT INTO `log` VALUES (21862, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:01:51');
INSERT INTO `log` VALUES (21863, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:03:09');
INSERT INTO `log` VALUES (21864, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:03:51');
INSERT INTO `log` VALUES (21865, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:04:30');
INSERT INTO `log` VALUES (21866, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:13:13');
INSERT INTO `log` VALUES (21867, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:13:35');
INSERT INTO `log` VALUES (21868, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:13:47');
INSERT INTO `log` VALUES (21869, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:14:07');
INSERT INTO `log` VALUES (21870, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:19:14');
INSERT INTO `log` VALUES (21871, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:21:36');
INSERT INTO `log` VALUES (21872, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:25:22');
INSERT INTO `log` VALUES (21873, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:28:25');
INSERT INTO `log` VALUES (21874, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:31:57');
INSERT INTO `log` VALUES (21875, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:33:42');
INSERT INTO `log` VALUES (21876, '获取用户信息', NULL, '[]', 'INFO', 'cn.xiaosm.cloud.core.controller.UserController.userInfo', 2, 0, '127.0.0.1', '2023-04-01 18:35:20');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `type` tinyint NULL DEFAULT 1 COMMENT '菜单类型',
  `parent_menu_id` int NULL DEFAULT 0 COMMENT '父级菜单，如果为0则表示为顶级菜单',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端图标',
  `order` tinyint NULL DEFAULT NULL COMMENT '排序',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `component` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称（文件名）',
  `permission` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态',
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `permission_unique`(`permission`) USING BTREE,
  INDEX `parent_menu_id_fk`(`parent_menu_id`) USING BTREE,
  CONSTRAINT `self_parent_menu` FOREIGN KEY (`parent_menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '根目录', 1, NULL, NULL, 0, NULL, NULL, NULL, 1, '2020-06-15 09:18:15', '2020-06-15 09:18:19');
INSERT INTO `menu` VALUES (2, '系统管理', 1, 1, 'fa-gear', 1, 'system', '', NULL, 1, '2020-06-21 05:33:29', '2020-06-15 09:17:15');
INSERT INTO `menu` VALUES (3, '用户管理', 2, 2, 'fa-user', 1, 'user', 'User', NULL, 1, '2020-06-22 05:20:22', '2020-06-13 04:04:00');
INSERT INTO `menu` VALUES (4, '角色管理', 2, 2, 'fa-user-plus', 2, 'role', 'Role', NULL, 1, '2020-06-13 16:04:56', '2020-06-13 16:04:48');
INSERT INTO `menu` VALUES (5, '菜单管理', 2, 2, 'fa-list', 3, 'menu', 'Menu', NULL, 1, '2020-06-13 16:09:03', '2020-06-13 16:09:03');
INSERT INTO `menu` VALUES (6, '资源管理', 2, 2, 'fa-archive', 4, 'resource', 'Resource', NULL, 1, '2023-01-12 15:33:31', '2020-06-14 18:45:22');
INSERT INTO `menu` VALUES (7, '评论管理', 2, 2, 'fa-comment', 5, 'comment', 'Comment', NULL, 2, '2020-06-13 16:13:06', '2020-06-14 18:45:24');
INSERT INTO `menu` VALUES (8, '定时任务', 2, 2, 'fa-clock', 6, 'task', 'Task', NULL, 1, '2020-06-13 16:13:14', '2020-06-14 18:45:26');
INSERT INTO `menu` VALUES (9, '系统监控', 1, 1, 'fa-laptop', 3, 'monitor', '', NULL, 1, '2020-06-13 16:14:14', '2020-06-15 09:17:17');
INSERT INTO `menu` VALUES (10, '操作日志', 2, 9, 'fa-history', 3, 'operateLog', 'OperateLog', NULL, 1, '2020-06-13 16:14:00', '2020-06-13 16:14:00');
INSERT INTO `menu` VALUES (11, '异常日志', 2, 9, 'fa-bug', 3, 'exceptionLog', 'ExceptionLog', NULL, 1, '2020-06-13 16:14:00', '2020-06-13 16:14:00');
INSERT INTO `menu` VALUES (12, 'SQL监控', 2, 9, 'fa-coins', 4, 'druid', 'Druid', NULL, 1, NULL, NULL);
INSERT INTO `menu` VALUES (13, '主机状态', 1, 9, 'fa-heartbeat', 3, 'systemInfo', 'SystemInfo', NULL, 1, '2020-06-13 16:14:00', '2020-06-13 16:14:00');
INSERT INTO `menu` VALUES (14, '系统工具', 1, 1, 'fa-toolbox', 3, 'tools', '', NULL, 1, '2020-06-13 16:14:17', '2020-06-15 09:17:20');
INSERT INTO `menu` VALUES (15, 'SMTP管理', 2, 14, 'fa-envelope', 3, 'smtp', 'SMTP', NULL, 1, '2020-06-13 16:14:02', '2020-06-13 16:14:02');
INSERT INTO `menu` VALUES (16, '用户查询', 3, 3, NULL, 99, NULL, NULL, 'user:query', 1, '2020-06-22 05:20:32', NULL);
INSERT INTO `menu` VALUES (17, '用户添加', 3, 3, NULL, 98, NULL, NULL, 'user:add', 1, '2020-06-22 05:20:41', NULL);
INSERT INTO `menu` VALUES (18, '用户修改', 3, 3, NULL, 100, NULL, NULL, 'user:modify', 1, '2020-06-22 05:20:55', NULL);
INSERT INTO `menu` VALUES (19, '用户删除', 3, 3, NULL, 100, NULL, NULL, 'user:delete', 1, NULL, NULL);
INSERT INTO `menu` VALUES (20, '角色查询', 3, 4, NULL, 3, NULL, NULL, 'role:query', 1, '2020-06-21 02:43:29', NULL);
INSERT INTO `menu` VALUES (21, '角色添加', 3, 4, NULL, 1, NULL, NULL, 'role:add', 1, '2020-06-20 08:45:47', '2020-06-20 08:45:47');
INSERT INTO `menu` VALUES (35, '角色修改', 3, 4, NULL, 3, NULL, NULL, 'role:modify', 1, '2020-06-21 05:05:55', '2020-06-21 05:05:55');
INSERT INTO `menu` VALUES (36, '角色删除', 3, 4, NULL, 3, NULL, NULL, 'role:delete', 1, '2020-06-21 05:07:49', '2020-06-21 05:07:15');
INSERT INTO `menu` VALUES (37, '操作日志查询', 3, 10, NULL, 100, NULL, NULL, 'log:query', 1, '2020-06-27 01:02:55', '2020-06-27 00:58:46');
INSERT INTO `menu` VALUES (40, '操作日志删除', 3, 10, NULL, 100, NULL, NULL, 'log:delete', 1, '2020-06-27 01:03:01', '2020-06-27 00:59:34');
INSERT INTO `menu` VALUES (41, '测试菜单', 2, 14, 'fa-user', 3, 'test', 'Test', NULL, 0, '2021-01-25 13:33:23', '2020-06-29 15:36:40');
INSERT INTO `menu` VALUES (42, '测试目录', 1, 1, 'fa-user', 3, 'test1', NULL, NULL, 0, '2021-04-12 13:09:00', '2020-06-29 23:55:20');
INSERT INTO `menu` VALUES (47, '菜单查询', 3, 5, NULL, 3, NULL, NULL, 'menu:query', 1, '2021-04-12 16:11:34', '2021-04-12 16:11:34');
INSERT INTO `menu` VALUES (48, '菜单添加', 3, 5, NULL, 3, NULL, NULL, 'menu:add', 1, '2021-04-12 16:11:34', '2021-04-12 16:11:34');
INSERT INTO `menu` VALUES (49, '菜单修改', 3, 5, NULL, 3, NULL, NULL, 'menu:modify', 1, '2021-04-12 16:11:34', '2021-04-12 16:11:34');
INSERT INTO `menu` VALUES (50, '菜单删除', 3, 5, NULL, 3, NULL, NULL, 'menu:delete', 1, '2021-04-12 16:15:19', '2021-04-12 16:15:21');
INSERT INTO `menu` VALUES (51, '前端权限', 2, 1, 'fa-tent', 100, 'front', 'front', NULL, 1, '2023-01-12 15:48:20', '2023-01-12 15:37:27');
INSERT INTO `menu` VALUES (52, '资源上传', 3, 51, NULL, 3, NULL, NULL, 'resource:upload', 1, '2021-04-12 16:15:19', '2021-04-12 16:15:21');
INSERT INTO `menu` VALUES (53, '资源重命名', 3, 51, NULL, 3, NULL, NULL, 'resource:rename', 1, '2021-04-12 16:15:19', '2021-04-12 16:15:21');
INSERT INTO `menu` VALUES (54, '资源新建', 3, 51, NULL, 3, NULL, NULL, 'resource:create', 1, '2021-04-12 16:15:19', '2021-04-12 16:15:21');
INSERT INTO `menu` VALUES (55, '资源删除', 3, 51, NULL, 3, NULL, NULL, 'resource:delete', 1, '2021-04-12 16:15:19', '2021-04-12 16:15:21');
INSERT INTO `menu` VALUES (56, '资源预览', 3, 51, NULL, 3, NULL, NULL, 'resource:preview', 1, '2021-04-12 16:15:19', '2021-04-12 16:15:21');
INSERT INTO `menu` VALUES (57, '资源下载', 3, 51, NULL, 3, NULL, NULL, 'resource:download', 1, '2021-04-12 16:15:19', '2021-04-12 16:15:21');
INSERT INTO `menu` VALUES (61, '数据字典', 1, 1, 'fa-key', 3, 'props', NULL, NULL, 1, '2023-01-11 20:17:22', '2023-01-11 20:17:22');
INSERT INTO `menu` VALUES (62, '文件类型', 2, 61, 'fa-file', 3, 'filetype', 'FileType', NULL, 1, '2023-01-11 20:24:22', '2023-01-11 20:24:22');
INSERT INTO `menu` VALUES (63, '通用配置', 2, 61, 'fa-key', 2, 'default', 'Default', NULL, 1, '2023-01-11 22:39:30', '2023-01-11 22:39:30');
INSERT INTO `menu` VALUES (65, '资源分享', 3, 51, NULL, 3, NULL, NULL, 'resource:share', 1, '2023-01-12 15:41:38', '2023-01-12 15:41:38');
INSERT INTO `menu` VALUES (67, '资源加速', 3, 51, NULL, 3, NULL, NULL, 'resource:cdn', 1, '2023-01-12 15:46:07', '2023-01-12 15:46:07');

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `prop_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置名',
  `prop_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置类型',
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `prop_key_unique`(`prop_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of properties
-- ----------------------------
INSERT INTO `properties` VALUES (13, 'email_username', '', 'email', '2020-06-28 19:43:56');
INSERT INTO `properties` VALUES (14, 'email_password', null, 'email', '2020-06-28 19:43:56');
INSERT INTO `properties` VALUES (15, 'email_send_name', 'Cloud-Xiaosm', 'email', '2020-06-28 19:43:56');
INSERT INTO `properties` VALUES (16, 'email_server_host', null, 'email', '2020-06-28 19:43:56');
INSERT INTO `properties` VALUES (17, 'email_server_port', null, 'email', '2020-06-28 19:43:56');
INSERT INTO `properties` VALUES (18, 'email_ssl', 'true', 'email', '2020-06-28 19:43:56');
INSERT INTO `properties` VALUES (19, 'filetype_video', 'mp4', 'filetype', NULL);
INSERT INTO `properties` VALUES (20, 'filetype_image', 'jpg,jpeg,gif,png', 'filetype', NULL);
INSERT INTO `properties` VALUES (21, 'filetype_text', 'txt,md,js,java,cpp,c,py,go', 'filetype', NULL);
INSERT INTO `properties` VALUES (22, 'filetype_audio', 'mp3', 'filetype', NULL);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件存放路径',
  `hash` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件Hash',
  `size` bigint NULL DEFAULT NULL COMMENT '文件大小',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父级id',
  `user_id` int NULL DEFAULT NULL,
  `bucket_id` int NOT NULL,
  `ref_id` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name_user_bucket`(`name`, `user_id`, `bucket_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1641727703773188098 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (52, '向日葵.exe', '/59242b2e3e1149139d31325c6d4769a5.exe', '59242b2e3e1149139d31325c6d4769a5', 9571104, 'exe', 0, 2, 2, NULL, '2022-09-19 22:57:42', '2022-09-19 22:57:42');
INSERT INTO `resource` VALUES (53, '向日葵-32.exe', '/ff7b4297f31e4358bfb69667872141c3.exe', 'ff7b4297f31e4358bfb69667872141c3', 26676958, 'exe', 0, 2, 2, NULL, '2022-09-19 22:57:42', '2022-09-19 22:57:42');
INSERT INTO `resource` VALUES (58, 'apache-tomcat-8.0.23.zip', '/cb735a865a184163b1772e07374f49b2.zip', 'cb735a865a184163b1772e07374f49b2', 9669531, 'zip', 0, 2, 2, NULL, '2022-09-27 16:01:23', '2022-09-27 16:01:23');
INSERT INTO `resource` VALUES (70, 'ces', NULL, NULL, NULL, 'dir', 0, 2, 2, NULL, '2022-10-05 14:50:18', '2022-10-05 14:50:18');
INSERT INTO `resource` VALUES (71, 'ces2', NULL, NULL, NULL, 'dir', 70, 2, 2, NULL, '2022-10-05 14:50:18', '2022-10-05 14:50:18');
INSERT INTO `resource` VALUES (73, 'OldFormJson.txt', '/2022-10/40c6bcefa0ca4c189454a55f322b23ce.txt', '40c6bcefa0ca4c189454a55f322b23ce', 61918, 'txt', 70, 2, 2, NULL, '2022-10-05 18:17:34', '2022-10-05 18:17:34');
INSERT INTO `resource` VALUES (103, '作业', NULL, NULL, 0, 'dir', 0, 2, 2, NULL, '2022-11-06 15:45:15', '2022-11-06 15:45:15');
INSERT INTO `resource` VALUES (112, 'mac.ovpn', '/2022-11/951a82b15df84580a69d8b5cc683830d.ovpn', '951a82b15df84580a69d8b5cc683830d', 4869, 'ovpn', 0, 3, 3, NULL, '2022-11-13 13:05:24', '2022-11-13 13:05:24');
INSERT INTO `resource` VALUES (113, 'pc.ovpn', '/2022-11/38d20e82367b4356b802a99e9b09d575.ovpn', '38d20e82367b4356b802a99e9b09d575', 4865, 'ovpn', 0, 3, 3, NULL, '2022-11-13 13:05:36', '2022-11-13 13:05:36');
INSERT INTO `resource` VALUES (117, '数据挖掘', NULL, NULL, NULL, 'dir', 103, 2, 2, NULL, '2022-11-23 16:37:06', '2022-11-23 16:37:06');
INSERT INTO `resource` VALUES (118, 'DRUG.txt', '/2022-11/ba16c0e625824c15b51e67246a01d7ae.txt', 'ba16c0e625824c15b51e67246a01d7ae', 8028, 'txt', 117, 2, 2, NULL, '2022-11-23 16:37:11', '2022-11-23 16:37:11');
INSERT INTO `resource` VALUES (1595740418898825217, 'test', NULL, NULL, NULL, 'dir', 0, 1, 1, NULL, '2022-11-24 19:25:38', '2022-11-24 19:25:38');
INSERT INTO `resource` VALUES (1595790575942127617, 'ces1', NULL, NULL, NULL, 'dir', 1595740418898825217, 1, 1, NULL, '2022-11-24 22:44:56', '2022-11-24 22:44:56');
INSERT INTO `resource` VALUES (1595790605763629058, 'ces2', NULL, NULL, NULL, 'dir', 1595740418898825217, 1, 1, NULL, '2022-11-24 22:45:03', '2022-11-24 22:45:03');
INSERT INTO `resource` VALUES (1595790783233019906, 'ces1-ces1', NULL, NULL, NULL, 'dir', 1595790575942127617, 1, 1, NULL, '2022-11-24 22:45:46', '2022-11-24 22:45:46');
INSERT INTO `resource` VALUES (1596104032407240705, 'ces1', '', NULL, NULL, 'dir', 1596104031849398273, 1, 1, NULL, '2022-11-24 22:44:56', '2022-11-25 19:30:30');
INSERT INTO `resource` VALUES (1596104032935723010, 'ces1-ces1', '', NULL, NULL, 'dir', 1596104032407240705, 1, 1, NULL, '2022-11-24 22:45:46', '2022-11-25 19:30:30');
INSERT INTO `resource` VALUES (1596104033460011009, 'pc.ovpn', '/2022-11/8154c64a80fa4652a18e9400a2850f3b.ovpn', '8154c64a80fa4652a18e9400a2850f3b', 4865, 'ovpn', 1596104032407240705, 1, 1, 114, '2022-11-13 13:06:28', '2022-11-25 19:30:30');
INSERT INTO `resource` VALUES (1596104033845886977, 'ces2', '', NULL, NULL, 'dir', 1596104031849398273, 1, 1, NULL, '2022-11-24 22:45:03', '2022-11-25 19:30:31');
INSERT INTO `resource` VALUES (1596104034370174978, 'pc.ovpn', '/2022-11/8154c64a80fa4652a18e9400a2850f3b.ovpn', '8154c64a80fa4652a18e9400a2850f3b', 4865, 'ovpn', 1596104031849398273, 1, 1, 114, '2022-11-13 13:06:28', '2022-11-25 19:30:31');
INSERT INTO `resource` VALUES (1596387233998491649, 'test2', NULL, NULL, NULL, 'dir', 0, 1, 1, NULL, '2022-11-26 14:15:51', '2022-11-26 14:15:51');
INSERT INTO `resource` VALUES (1596387254575747074, 'test', NULL, NULL, NULL, 'dir', 1596387233998491649, 1, 1, NULL, '2022-11-26 14:15:56', '2022-11-26 14:15:56');
INSERT INTO `resource` VALUES (1596390443320524801, 'cChat.jks', '/2022-11/cde0566cb278420eab2dc8d738f65940.jks', 'cde0566cb278420eab2dc8d738f65940', 3390, 'jks', 1595740418898825217, 1, 1, NULL, '2022-11-26 14:28:36', '2022-11-26 14:28:36');
INSERT INTO `resource` VALUES (1596792584365248514, '20200629184107_tTfvw1.jpeg', '/2022-11/3cb028a8ddd244aabfe7481de4534cc2.jpeg', '3b7d2ae4a47d032b48f3aeefd35af54e', 32441, 'jpg', 1596387233998491649, 1, 1, NULL, '2022-11-28 01:03:11', '2022-11-27 17:06:34');
INSERT INTO `resource` VALUES (1597471703390326786, 'apache-tomcat-8.0.23.zip', '/cb735a865a184163b1772e07374f49b2.zip', 'cb735a865a184163b1772e07374f49b2', 9669531, 'zip', 103, 2, 2, NULL, '2022-09-28 00:01:23', '2022-11-29 14:05:08');
INSERT INTO `resource` VALUES (1597472382955655170, 'license.txt', '/2022-11/6432a961f37f4250a44b060f07c6fcab.txt', '408ea62310f873547d7a8de356d7738a', 8, 'txt', 0, 3, 3, NULL, '2022-11-29 14:08:20', '2022-11-29 22:07:50');
INSERT INTO `resource` VALUES (1602856188117041153, '7z2201-x64.exe', '/2022-12/1ff732dfa6d54f209d25e64e504d0c3d.exe', 'a6a0f7c173094f8dafef996157751ecf', 1575742, 'exe', 0, 2, 2, NULL, '2022-12-14 10:41:10', '2022-12-14 10:41:10');
INSERT INTO `resource` VALUES (1611663990492073986, '远控.zip', '/2023-01/4b2d987e0e80419f8dfacb0bc8bcaa63.zip', 'ea676927d488ce6d73ae768f5885e479', 39394100, 'zip', 0, 2, 2, NULL, '2023-01-07 18:00:13', '2023-01-07 18:00:13');
INSERT INTO `resource` VALUES (1613407585795436546, 'Snipaste_2022-12-15_11-06-25.png', '2023-01/77a78cabc9094e4bb4dc548ac9a97905.png', 'e71bf8e3da1ec81a4641a998dbdb0c79', 37066, 'png', 0, 2, 2, NULL, '2023-01-12 13:28:39', '2023-01-12 13:28:39');
INSERT INTO `resource` VALUES (1613410702792454146, 'avatar.jpg', '2023-01/c3741fb6e9c740bebcf06d5879b5d93b.jpg', 'edfd8c2fd41e8ac5af60cce486f955c3', 43247, 'jpg', 0, 2, 2, NULL, '2023-01-12 13:41:02', '2023-01-12 13:41:02');
INSERT INTO `resource` VALUES (1613423741394604033, 'i4Tools_v7.98.70_Setup.exe', '/2023-01/b730250a3d59ad203cec9dcbfa628781.exe', 'b730250a3d59ad203cec9dcbfa628781', 148141432, 'exe', 0, 2, 2, NULL, '2023-01-12 14:32:51', '2023-01-12 14:32:51');
INSERT INTO `resource` VALUES (1614154807546843137, 'C8909F06-110E-4F9D-B1D0-0C3581779EEA.png', '/2023-01/b3c8df76a9754af88bfea5f4216f71b3.png', 'b2200f0094bcc32fe4e3bd025b57ccea', 6414614, 'png', 0, 2, 2, NULL, '2023-01-14 14:57:50', '2023-01-14 14:57:50');
INSERT INTO `resource` VALUES (1616350572037902338, '花玲 _ 喵酱油 _ 宴宁 _ Kinsen - 让风告诉你.ogg', '2023-01/6d10d2a6db6343fa9710e1d1350d38f1.ogg', 'e9f0efb00a70ff13f18c4d1d2d882ae2', 9473311, 'ogg', 0, 1, 1, NULL, '2023-01-20 16:23:01', '2023-01-20 16:23:01');
INSERT INTO `resource` VALUES (1616357217522520066, '51单片机介绍（一）.avi', '/2023-01/654865571b0876b35fb7002a95508f6e.avi', '654865571b0876b35fb7002a95508f6e', 89565696, 'avi', 0, 1, 1, NULL, '2023-01-20 16:49:26', '2023-01-20 16:49:26');
INSERT INTO `resource` VALUES (1616364777407352834, '55B4B59F-D038-48DA-BA72-275197B3F63F.jpeg', '2023-01/0b20324c18aa4f3680d2b133e656e9f9.jpeg', '83ef30878481ffa4f8cb7e4eb99b344e', 207968, 'jpg', 0, 2, 2, NULL, '2023-01-20 17:19:28', '2023-01-20 17:19:28');
INSERT INTO `resource` VALUES (1616369602341736450, '17年前的黑科技mp4，居然有20g的内存，神一样存在！,科学,科普,好看视频.mp4', '2023-01/c21ce7a760464bb2a6a7cf5147948d9c.mp4', 'dbce3c853c30079a59b977f345a9fda3', 39140973, 'mp4', 0, 1, 1, NULL, '2023-01-20 17:38:39', '2023-01-20 17:38:39');
INSERT INTO `resource` VALUES (1622201878816329730, '视频', NULL, NULL, NULL, 'dir', 0, 2, 2, NULL, '2023-02-05 19:54:02', '2023-02-05 19:54:02');
INSERT INTO `resource` VALUES (1622202635762372609, '三体', NULL, NULL, NULL, 'dir', 1622201878816329730, 2, 2, NULL, '2023-02-05 19:57:02', '2023-02-05 19:57:02');
INSERT INTO `resource` VALUES (1622202897277227010, '电视剧《三体第22集》.mp4', '2023-02/ccad9cb82ea3e7a4145f3c485914aa73.mp4', 'ccad9cb82ea3e7a4145f3c485914aa73', 164954233, 'mp4', 1622202635762372609, 2, 2, NULL, '2023-02-06 03:58:04', '2023-02-06 03:58:04');
INSERT INTO `resource` VALUES (1622204026014765058, 'Screenshot_2023-02-04-14-15-23-610_com.tencent.qqlive.jpg', '2023-02/cef5b8cedf294998be4513d4df3e84b3.jpg', '515ec6cdce29dfa010899ceb90ace107', 630823, 'jpg', 0, 2, 2, NULL, '2023-02-05 20:02:34', '2023-02-05 20:02:34');
INSERT INTO `resource` VALUES (1623336459787407362, 'v2rayN-With-Core.7z', '2023-02/eec4b97c380b4fae812ea39ee1041655.7z', '7052b54c64ecb5d72b7ab6d5babc72d7', 60091034, '7z', 1623684299000745986, 2, 2, NULL, '2023-02-09 07:02:27', '2023-02-09 07:02:27');
INSERT INTO `resource` VALUES (1623684299000745986, 'APP', NULL, NULL, NULL, 'dir', 0, 2, 2, NULL, '2023-02-09 22:04:38', '2023-02-09 22:04:38');
INSERT INTO `resource` VALUES (1623685155041415170, 'Clash.for.Windows.Setup.0.15.10.exe', '/2023-02/2db1be923f2b51077e276147dde574d5.exe', '2db1be923f2b51077e276147dde574d5', 101988248, 'exe', 1623684299000745986, 2, 2, NULL, '2023-02-09 22:08:02', '2023-02-09 22:08:02');
INSERT INTO `resource` VALUES (1624429418543849474, 'backiee-121292-landscape.jpg', '2023-02/69afa07bb9a640ce9c787e5a94d6982d.jpg', '6e970e0aa0889269f1e1b3fe851fdfce', 617900, 'jpg', 0, 1, 1, NULL, '2023-02-11 23:25:28', '2023-02-11 23:25:28');
INSERT INTO `resource` VALUES (1624635587574206465, 'backiee-198031-landscape.jpg', '2023-02/de064cc0c3e1496f99543ce885038d59.jpg', 'c529e9a0a5b1a2cfc7e2776c2184bcab', 1021521, 'jpg', 0, 2, 2, NULL, '2023-02-12 13:04:43', '2023-02-12 13:04:43');
INSERT INTO `resource` VALUES (1629697044833734658, 'E5CEBE32-F8D3-4757-80F9-182AC747A9ED.jpeg', '2023-02/8e19179ec9be4b67a1d8db5fd1b7fecf.jpeg', '925c9a430afc96b3a08297df568e9e23', 235038, 'jpg', 0, 2, 2, NULL, '2023-02-26 12:17:08', '2023-02-26 12:17:08');
INSERT INTO `resource` VALUES (1639126442813001730, 'go1.20.windows-amd64.zip', '2023-03/37bf36a944b1784b4f41290a9ea41c3e.zip', '37bf36a944b1784b4f41290a9ea41c3e', 113293218, 'zip', 0, 1, 1, NULL, '2023-03-24 12:46:12', '2023-03-24 12:46:12');
INSERT INTO `resource` VALUES (1639128751492141057, '123.java', '2023-03/715e6f3434be411dad5d5c4f89eae1ad.txt', '715e6f3434be411dad5d5c4f89eae1ad', 105, 'txt', 0, 1, 1, NULL, '2023-03-31 16:52:37', '2023-03-24 20:55:23');
INSERT INTO `resource` VALUES (1641727703773188097, 'EsLog.java', '2023-03/f01842e89ed14be188db3ff6580bae94.java', 'f01842e89ed14be188db3ff6580bae94', 497, 'java', 0, 1, 1, NULL, '2023-03-31 17:03:09', '2023-04-01 01:02:41');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `name_zh` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色中文名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态',
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_admin', '管理员', '-', 1, '2020-06-18 04:07:18', '2020-06-13 16:19:41');
INSERT INTO `role` VALUES (2, 'ROLE_normal', '注册用户', '', 1, '2023-01-12 15:49:00', NULL);
INSERT INTO `role` VALUES (3, 'ROLE_share', '分享用户', '-', 1, '2021-03-29 18:38:46', '2020-06-14 05:23:29');
INSERT INTO `role` VALUES (4, 'ROLE_public', '公共用户', '', 1, '2023-01-12 15:49:27', NULL);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int NULL DEFAULT NULL COMMENT '角色id',
  `menu_id` int NULL DEFAULT NULL COMMENT '菜单id',
  INDEX `role_fk`(`role_id`) USING BTREE,
  INDEX `menu_fk`(`menu_id`) USING BTREE,
  CONSTRAINT `menu_fk` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (3, 57);
INSERT INTO `role_menu` VALUES (2, 65);
INSERT INTO `role_menu` VALUES (2, 51);
INSERT INTO `role_menu` VALUES (2, 67);
INSERT INTO `role_menu` VALUES (2, 52);
INSERT INTO `role_menu` VALUES (2, 53);
INSERT INTO `role_menu` VALUES (2, 54);
INSERT INTO `role_menu` VALUES (2, 55);
INSERT INTO `role_menu` VALUES (2, 56);
INSERT INTO `role_menu` VALUES (2, 57);
INSERT INTO `role_menu` VALUES (4, 51);
INSERT INTO `role_menu` VALUES (4, 52);
INSERT INTO `role_menu` VALUES (4, 53);
INSERT INTO `role_menu` VALUES (4, 55);
INSERT INTO `role_menu` VALUES (4, 56);
INSERT INTO `role_menu` VALUES (4, 57);

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `auto_id` int NOT NULL AUTO_INCREMENT,
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `short_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deadline` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `password` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `view_count` int NULL DEFAULT 0 COMMENT '浏览次数',
  `download_count` int NULL DEFAULT 0 COMMENT '下载次数',
  `resource_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`auto_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of share
-- ----------------------------
INSERT INTO `share` VALUES (5, '1193c264b1ce4a43b6afc86730c5670f', NULL, NULL, '1234', 0, 0, '1596791736046292994', 1, '2022-11-27 17:34:47');
INSERT INTO `share` VALUES (6, '306fc8b71d864200a8bc1a5ff3fe5af3', NULL, NULL, '3jtf', 0, 0, '1598634997868384258', 2, '2022-12-02 20:51:47');
INSERT INTO `share` VALUES (7, '976955af33964c0d9d2dc3b85feee3cf', NULL, NULL, NULL, 0, 0, '1598634997868384258', 2, '2022-12-02 21:01:51');
INSERT INTO `share` VALUES (8, '5fca03e92cf84791ab27c9980e3ff84b', NULL, NULL, 'txhj', 0, 0, '1596791736046292994', 1, '2022-12-02 21:14:45');
INSERT INTO `share` VALUES (9, '2663015a11564e3e9adac0ce5e6a107e', NULL, '2022-12-03 00:00:00', '3uo4', 0, 0, '1596791736046292994', 1, '2022-12-02 21:14:59');
INSERT INTO `share` VALUES (10, 'b641489c467246aaad9fb9019f2fbd3c', NULL, '2022-12-04 00:00:00', 'tsp8', 0, 0, '1596791736046292994', 1, '2022-12-02 21:16:24');
INSERT INTO `share` VALUES (11, '39cb23eedf3b44ef98863d1205de715b', NULL, '2022-12-03 00:00:00', 'tlwe', 0, 0, '1596791736046292994', 1, '2022-12-02 21:16:49');
INSERT INTO `share` VALUES (12, 'f0a22409820640288e74ab0ea2b42fd2', NULL, '2022-12-03 08:00:00', NULL, 0, 0, '1596791736046292994', 1, '2022-12-02 21:21:28');
INSERT INTO `share` VALUES (13, '89a91de3a75a4f208e2f8a339b6686de', NULL, NULL, '1231', 0, 0, '1596791736046292994', 1, '2022-12-02 21:21:56');
INSERT INTO `share` VALUES (14, '045786e2e9b346109adac0dab65cd533', NULL, NULL, 'dzbn', 0, 0, '1596791736046292994', 1, '2022-12-02 21:24:21');
INSERT INTO `share` VALUES (15, 'cde6135b06c3464fa72b2b877240f992', NULL, NULL, 'rs8z', 0, 0, '1596387233998491649', 1, '2022-12-02 21:27:38');
INSERT INTO `share` VALUES (16, 'e8facc96752e46479c65b237a0a43239', NULL, NULL, 'bjyw', 0, 0, '1596791736046292994', 1, '2022-12-02 21:29:22');
INSERT INTO `share` VALUES (17, '207a3ed43c5545b8bcbd00700779dac4', NULL, NULL, '5exw', 0, 0, '1598634997868384258', 2, '2022-12-02 21:33:57');
INSERT INTO `share` VALUES (18, 'fdca4f0cab4047dd91ab94a939c0a139', NULL, NULL, 'xjpf', 0, 0, '1598634997868384258', 2, '2022-12-02 21:36:15');
INSERT INTO `share` VALUES (19, '098ada3e63e7424caf623154c169884b', NULL, NULL, 'zqw3', 0, 0, '1598634997868384258', 2, '2022-12-02 21:37:47');
INSERT INTO `share` VALUES (20, '4d1a2f9bd66449a1824180d4ca0fd581', NULL, NULL, '7rab', 0, 0, '1596791736046292994', 1, '2022-12-02 21:42:08');
INSERT INTO `share` VALUES (21, 'b54336e006124631ba1b17e8eb9c45b5', NULL, NULL, 'h5r1', 0, 0, '1596791736046292994', 1, '2022-12-02 21:49:22');
INSERT INTO `share` VALUES (22, '002819619ffd488f94a7828c84e92973', NULL, NULL, 'h5r1', 0, 0, '1596791736046292994', 1, '2022-12-02 21:51:07');
INSERT INTO `share` VALUES (23, '0283a48408f34de78cab8957f6fb5aff', NULL, NULL, 'h5r1', 0, 0, '1596791736046292994', 1, '2022-12-02 21:51:28');
INSERT INTO `share` VALUES (24, '4d0098428b7342aab3790dddbd9b4eee', NULL, NULL, 'dmcl', 0, 0, '1596791736046292994', 1, '2022-12-02 21:51:45');
INSERT INTO `share` VALUES (25, 'f78482cc9e2d435ca9e8fcf791da5fc3', NULL, NULL, 'j810', 0, 0, '1598634997868384258', 2, '2022-12-02 22:56:28');
INSERT INTO `share` VALUES (26, '72fb0b9b2d6f4b82ad04a9f997f0c2f4', NULL, NULL, 'y0dg', 0, 0, '1602886602610282498', 2, '2022-12-14 12:42:26');
INSERT INTO `share` VALUES (27, 'd85a186644084ba69fe04315761411bb', NULL, NULL, '8b6j', 0, 0, '1602856188117041153', 2, '2022-12-14 12:42:45');
INSERT INTO `share` VALUES (28, '056020b3911e46aeba426e992401b63a', NULL, NULL, '1234', 0, 0, '1602856188117041153', 2, '2022-12-14 12:43:03');
INSERT INTO `share` VALUES (29, '1451ed58b7534dc18ba4836856562316', NULL, NULL, '1234', 0, 0, '1608679276234670082', 1, '2022-12-30 12:20:12');
INSERT INTO `share` VALUES (30, '1ea6d6c076bf4148b4649f363c1b95e1', NULL, '2023-01-21 08:00:00', NULL, 0, 0, '1616369602341736450', 1, '2023-01-20 16:55:42');
INSERT INTO `share` VALUES (31, '995399c02362495483318d8e1083e372', NULL, NULL, '8i6e', 0, 0, '1622201878816329730', 2, '2023-02-12 11:56:57');
INSERT INTO `share` VALUES (32, 'c7f254e83e39424abf12d0d878cd2c5f', NULL, '2023-02-17 16:00:00', NULL, 0, 0, '1622201878816329730', 2, '2023-02-12 12:30:53');
INSERT INTO `share` VALUES (33, 'c55434b8ece74707bb137d0df1690ad0', NULL, NULL, NULL, 0, 0, '1616350572037902338', 1, '2023-02-12 14:45:28');
INSERT INTO `share` VALUES (34, '9afaec1c182440afa3d503b94775bc3b', NULL, NULL, 'qi1c', 0, 0, '1639128751492141057', 1, '2023-03-24 13:08:25');
INSERT INTO `share` VALUES (35, '342543e200f24f028fe5944d054de1cd', NULL, NULL, 'og1h', 0, 0, '1641727703773188097', 1, '2023-03-31 17:05:03');
INSERT INTO `share` VALUES (36, '92f427a95a174edbbfea34457b97530c', NULL, NULL, 'blyl', 0, 0, '1616350572037902338', 1, '2023-03-31 17:05:57');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `cron` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全限定类名',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `user_id` int NULL DEFAULT NULL COMMENT '创建用户的id',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `task_user_id_fk`(`user_id`) USING BTREE,
  CONSTRAINT `task_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (1, '心跳监控', '0/10 * * * * ?', '监控系统状态', 'cn.xiaosm.yadmin.scheduler.EmailTask', NULL, 1, 0, '2020-06-30 15:47:24', '2020-06-28 11:27:34');
INSERT INTO `task` VALUES (2, '测试任务', '0/1 * * * * ?', NULL, 'cn.xiaosm.yadmin.scheduler.EmailTask', NULL, 1, 2, '2020-06-30 15:47:27', '2021-04-16 12:40:07');
INSERT INTO `task` VALUES (5, '心跳监控2', '0/10 * * * * ?', NULL, 'HeartTask', NULL, 1, 1, '2021-04-16 12:40:04', '2021-04-14 14:58:37');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uuid` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '每次登录的uuid',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称或姓名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `age` tinyint NULL DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `status` tinyint NULL DEFAULT 1 COMMENT '用户状态',
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_unique`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '34f53267-c61a-45de-838e-8d0d7d92eb04', 'admin', '不是木易杨', '$2a$10$i7ghCgpfNcT.Uw9M/XkWSuaKO/PhmT/dktvQx3aUwn69u4YXCtZFG', '111@qq.com', '男', 18, 'https://q1.qlogo.cn/g?b=qq&nk=1282381264&s=640', 1, '2021-04-16 23:32:22', '2020-06-01 08:00:00');
INSERT INTO `user` VALUES (2, 'b7915ff3-6401-4df9-949a-d166e73bcf9f', 'guest', '不是木易杨', 'xiaoyang', '111@qq.com', '男', 18, 'https://q.qlogo.cn/headimg_dl?dst_uin=1282381264&spec=640', 1, '2021-04-16 23:32:22', '2020-06-01 08:00:00');
INSERT INTO `user` VALUES (3, '3f8c0b33-a2df-48aa-af9f-419250c730fb', 'toyong', 'Toyong', '$2a$10$We3fk0Umg0HlV5.Wln4Jy.6xglgB4XD0RGm0Wp7G.0QdGp3jgXp9K', '111@qq.com', '男', 18, 'https://q.qlogo.cn/headimg_dl?dst_uin=1282381264&spec=640', 1, '2021-04-16 23:32:22', '2020-06-01 08:00:00');

-- ----------------------------
-- Table structure for user_login_track
-- ----------------------------
DROP TABLE IF EXISTS `user_login_track`;
CREATE TABLE `user_login_track`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `login_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `login_time` datetime NULL DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `track_user_id_fk`(`user_id`) USING BTREE,
  CONSTRAINT `track_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 320 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_login_track
-- ----------------------------
INSERT INTO `user_login_track` VALUES (302, 1, '127.0.0.1', '2022-03-23 19:31:07');
INSERT INTO `user_login_track` VALUES (303, 1, '127.0.0.1', '2022-04-05 17:26:04');
INSERT INTO `user_login_track` VALUES (304, 1, '127.0.0.1', '2022-04-05 17:39:06');
INSERT INTO `user_login_track` VALUES (305, 1, '172.20.0.1', '2022-11-21 13:57:22');
INSERT INTO `user_login_track` VALUES (306, 1, '0:0:0:0:0:0:0:1', '2023-01-11 19:23:32');
INSERT INTO `user_login_track` VALUES (307, 1, '0:0:0:0:0:0:0:1', '2023-01-11 19:38:38');
INSERT INTO `user_login_track` VALUES (308, 1, '0:0:0:0:0:0:0:1', '2023-01-11 20:03:37');
INSERT INTO `user_login_track` VALUES (309, 1, '0:0:0:0:0:0:0:1', '2023-01-11 20:25:28');
INSERT INTO `user_login_track` VALUES (310, 1, '0:0:0:0:0:0:0:1', '2023-01-11 21:41:15');
INSERT INTO `user_login_track` VALUES (311, 1, '0:0:0:0:0:0:0:1', '2023-01-11 21:53:42');
INSERT INTO `user_login_track` VALUES (312, 1, '0:0:0:0:0:0:0:1', '2023-01-11 22:03:43');
INSERT INTO `user_login_track` VALUES (313, 1, '0:0:0:0:0:0:0:1', '2023-01-11 22:39:44');
INSERT INTO `user_login_track` VALUES (314, 1, '0:0:0:0:0:0:0:1', '2023-01-11 22:53:19');
INSERT INTO `user_login_track` VALUES (315, 1, '0:0:0:0:0:0:0:1', '2023-01-12 15:32:22');
INSERT INTO `user_login_track` VALUES (316, 1, '0:0:0:0:0:0:0:1', '2023-01-12 17:59:56');
INSERT INTO `user_login_track` VALUES (317, 1, '0:0:0:0:0:0:0:1', '2023-01-20 10:55:05');
INSERT INTO `user_login_track` VALUES (318, 1, '172.20.0.1', '2023-02-26 11:25:34');
INSERT INTO `user_login_track` VALUES (319, 1, '172.20.0.1', '2023-02-26 11:26:52');

-- ----------------------------
-- Table structure for user_open
-- ----------------------------
DROP TABLE IF EXISTS `user_open`;
CREATE TABLE `user_open`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `open_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快捷登录平台中的用户id',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '快捷登录平台',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态',
  `update_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `open_userId_fk`(`user_id`) USING BTREE,
  CONSTRAINT `open_userId_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_open
-- ----------------------------
INSERT INTO `user_open` VALUES (1, 1, '', 'QQ', 'today', 'http://thirdqq.qlogo.cn/g?b=oidb&k=ZGIq18FHUJLrUSWo4HrO1w&s=100&t=1617554377', 1, '2021-04-16 23:32:30', '2021-04-16 23:32:30');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id_fk`(`user_id`) USING BTREE,
  INDEX `role_id_fk`(`role_id`) USING BTREE,
  CONSTRAINT `role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 2, 4);
INSERT INTO `user_role` VALUES (3, 3, 2);

-- ----------------------------
-- Procedure structure for selectByBucketAndFullPath
-- ----------------------------
DROP PROCEDURE IF EXISTS `selectByBucketAndFullPath`;
delimiter ;;
CREATE PROCEDURE `selectByBucketAndFullPath`(in fullPath VARCHAR(255))
begin
     SELECT * FROM resource WHERE path = fullPath;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
