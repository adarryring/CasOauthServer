# tomcat https #

## Java生成证书
```
rem cmd命令行
set filename_key=c:\key\login_key
set filename_crt=c:\key\login_key
keytool -genkey -alias login_key -keyalg RSA -keypass xiaohong -validity 365 -keystore %filename_key%.keystore -storepass xiaohong
login.xh.com
xh.com
xh
GD
GD
CN
y
rem 导出
keytool -export -alias login_key -keystore %filename_key%.keystore -file %filename_crt%.cer -storepass xiaohong
rem 管理员命令行
rem del "%JAVA_HOME%\jre\lib\security\cacerts"
keytool -import -trustcacerts -alias login_key -keystore "%JAVA_HOME%/jre/lib/security/cacerts" -file %filename_crt%.cer -storepass xiaohong
y
```

## Tomcat配置https
- `conf/server.xml`
```xml
<Connector SSLEnabled="true" URIEncoding="UTF-8" clientAuth="false" keystoreFile="c:/key/login_key.keystore" keystorePass="xiaohong" maxThreads="150" port="9251" protocol="org.apache.coyote.http11.Http11Protocol" scheme="https" secure="true" sslProtocol="TLS"/>
```
