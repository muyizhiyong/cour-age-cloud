## 
### 认证模块

使用keytool生成RSA证书jwt.jks，复制到resource目录下，在JDK的bin目录下使用如下命令即可；
keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
