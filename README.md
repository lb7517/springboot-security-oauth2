## springboot security + oauth2实现安全登入

---

### 四种模式访问地址
> * 【密码授权模式-client】
密码模式需要参数：username,password,grant_type,client_id,client_secret
http://localhost:8080/oauth/token?username=demoUser1&password=123456&grant_type=password&client_id=demoApp&client_secret=demoAppSecret

>* 【客户端授权模式-password】
客户端模式需要参数：grant_type,client_id,client_secret
http://localhost:8080/oauth/token?grant_type=client_credentials&client_id=demoApp&client_secret=demoAppSecret

>* 【授权码模式-code】
获取code
http://localhost:8080/oauth/authorize?response_type=code&client_id=demoApp&redirect_uri=http://baidu.com

>* 通过code换token
http://localhost:8080/oauth/token?grant_type=authorization_code&code=Filepd&client_id=demoApp&client_secret=demoAppSecret&redirect_uri=http://baidu.com

#### 授权码模式具体执行过程
> 1. 获取code
http://localhost:8080/oauth/authorize?response_type=code&client_id=demoApp&redirect_uri=http://baidu.com
http://baidu.com/?code=a7xs6P


> 2. 获取token
http://localhost:8080/oauth/token?grant_type=authorization_code&code=P6KMcE&client_id=demoApp&client_secret=demoAppSecret&redirect_uri=http://baidu.com

> 3. 通过获取的access_token访问接口


### 注意事项
> * 报以下错误，需添加tomcat 包[spring-boot-starter-tomcat](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-tomcat)

	Error:(15, 8) java: 无法访问javax.servlet.Filter
	找不到javax.servlet.Filter的类文件

> * 注意SecurityConfiguration类中AuthenticationManagerBuilder设置内存用户名密码格式，必选要实现passwordEncoder(new MyPasswordEncoder())自定义类或者直接用
new BCryptPasswordEncoder()

> * 注意授权类OAuth2ServerConfig中客户端ClientDetailsServiceConfigurer设置，
.secret()中的内容必须要用 PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("secret")  PasswordEncoderFactories进行编码， 否则会报以下错误:
java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"

> * 注意OAuth2ServerConfig中client中的withClient和secret配置 请求获取access_token时要对应
