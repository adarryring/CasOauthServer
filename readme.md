# CasOauthServer #

## Introduction
SSO单点登录系统，支持Oauth2.0第三方配置，将帐号登录从业务系统中分离出来，形成用户中心，用户管理维护性更高。

## Documentation
- 参考官网 : https://www.apereo.org/projects/cas

## Features
- 单点登录
- oauth2.0
- 底层为 `jasig cas`
- 整合pac4j

## Capture
![](https://raw.githubusercontent.com/xiaohong2019/CasOauthServer/master/document/images/201805091222028.png)
![](https://raw.githubusercontent.com/xiaohong2019/CasOauthServer/master/document/images/201805091226026.png)
![](https://raw.githubusercontent.com/xiaohong2019/CasOauthServer/master/document/images/201805091227020.png)

## Project Design

### 三方体系
- 架构设计 : web-3.0/spring-3.2/hibernate-4.0/pac4j-1.4/jasig-4.0
- 软件设计 : JDK-1.8/Tomcat-8/Maven-3/MySQL-5.6

### 初始化数据库
- `document/sql/init-sql.sql`

### 数据库、oauth客户端配置信息
- `locate : src/config/database.properties`
- `locate : src/config/oauth.properties`

### hosts
- `login.xh.com local.xh.com 127.0.0.1`

### 日志
- `src/config/casOauthServer/message/log4j.xml`
```xml
<!-- 日志路径 -->
<appender name="cas" class="org.apache.log4j.RollingFileAppender">
    <param name="File" value="D:/cas.log" />
    <param name="MaxFileSize" value="512KB" />
    <param name="MaxBackupIndex" value="3" />
    <layout class="org.apache.log4j.PatternLayout">
        <param name="ConversionPattern" value="%d %p [%c] - %m%n"/>
    </layout>
</appender>
```

### 外部oauth客户端配置bean
- `locate : src/config/spring-configuration/applicationContext.xml`
```xml
<!-- oauth -->
<beans>
    <bean id="oauth_client1" class="org.pac4j.oauth.client.CasOAuthWrapperClient">
        <property name="key" value="${oauth.client1.key}"/>
        <property name="secret" value="${oauth.client1.secret}"/>
        <property name="casOAuthUrl" value="${oauth.client1.casOAuthUrl}"/>
    </bean>
    <bean id="clients" class="org.pac4j.core.client.Clients">
        <property name="callbackUrl" value="${cas.url}"/>
        <property name="clients">
            <list>
                <ref bean="oauth_client1"/>
            </list>
        </property>
    </bean>
</beans>
```

### 整合pac4j
- `src/config/casOauthServer/login-webflow.xml`
```xml
<!-- add clientAction -->
<bean id="clientAction" class="org.jasig.cas.support.pac4j.web.flow.ClientAction">
  <constructor-arg index="0" ref="centralAuthenticationService"/>
  <constructor-arg index="1" ref="clients"/>
</bean>
```
- `src/config/casOauthServer/cas-servlet.xml`
```xml
<!-- add clientAction -->
<action-state id="clientAction">
  <evaluate expression="clientAction" />
  <transition on="success" to="sendTicketGrantingTicket" />
  <transition on="error" to="ticketGrantingTicketCheck" />
  <transition on="stop" to="stopWebflow" />
</action-state>
```

### ui页面
`/WebRoot/WEB-INF/view/jsp/default/ui/*.jsp`

### 修复源文件情况
- `返回值bug，修改WebRoot/WEB-INF/view/jsp/protocol/2.0/casServiceValidationSuccess.jsp`
```jsp
<%-- 添加到cas:authenticationSuccess标签里面 --%>
<%-- 添加cas属性值，返回给用户端 --%>
<c:if test="${fn:length(assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.attributes) > 0}">
  <cas:attributes>
      <c:forEach var="attr" items="${assertion.chainedAuthentications[fn:length(assertion.chainedAuthentications)-1].principal.attributes}">
          <%-- 注意这里冒号后面不能有空格！ --%>
          <cas:${fn:escapeXml(attr.key)}>${fn:escapeXml(attr.value)}</cas:${fn:escapeXml(attr.key)}>
      </c:forEach>
  </cas:attributes>
</c:if>
```

## Communication
- 权限之争 650500861

## Copyright and License
code for free
