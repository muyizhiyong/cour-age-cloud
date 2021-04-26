# cour-auth

## 模块说明

- auth是一个权限模块
- 技术框架：SSM + spring security + oauth2 密码模式 + jwt + redis
- 功能接口：登录认证、接口鉴权、权限刷新。
- 启动方式：通过`AuthServerApplication#main()`启动
- 使用方式：在业务项目中添加zj-auth-admin依赖，启动业务项目的main方法
- 实现细节：
    - `LoginResourceImpl`中登录接口会对`127.0.0.1:8080`做回调，访问/oauth/token。
    - Jwt内的信息是无法被修改的，只有淘汰并刷新，最好由前端去调用refresh
    - Jwt淘汰方式，通过refresh接口
    - 权限缓存Hashmap更新要保证原子性和数据一致性
    - 多个JVM的情况下如何保证Hashmap缓存数据的一致性！（redis）
    - 如果是使用redis cluster 需要分析redlock和redis缓存版本号之间的并发问题


### 权限模块如何进行登录

```
curl -XPOST  /auth/login  -d {
    "username":"admin",
    "password":"12345678"
}
response demo:
{
    "retCode":"000000",
    "retMsg":"成功",
    "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTk0Mjk1MzMsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiNDM2OWRkNjAtNGQ5YS00MjM3LWE2MmMtMmZhNWEzY2Q3OGZhIiwiY2xpZW50X2lkIjoicGMtd2ViIiwic2NvcGUiOlsiYWxsIl19.Q-irSgkQ4zs3EMSXsl4cPAfpSkZ6SwD-RSoW5RmgYP8",
    "expires_in":"1799",
    "jti":"4369dd60-4d9a-4237-a62c-2fa5a3cd78fa",
    "refresh_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MTk0MzEzMzMsInVzZXJfbmFtZSI6ImFkbWluIiwianRpIjoiYTA4ZTcyNzUtYjZkZi00ZGViLWEyZjgtYTM3OTE2MTcwZGUzIiwiY2xpZW50X2lkIjoicGMtd2ViIiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjQzNjlkZDYwLTRkOWEtNDIzNy1hNjJjLTJmYTVhM2NkNzhmYSJ9.EbjOmDhpaBb_qWptbPwkrldF-MsugaajQ3G0kyoR7rI",
    "scope":"all",
    "token_type":"bearer"
}


```

### oauth2中的授权码模式

1. 访问：http://localhost:8080/oauth/authorize?response_type=code&client_id=my-client-1&scope=all&redirect_uri=http://localhost:9090/callback
2. 输入用户名密码，后台验证成功后，会将授权码通过 redirect_uri 回调返回，如：http://localhost:9090/callback?code=4R8OWh
3. 携带授权码，通过 /oauth/token 接口领取 token ，详情如下：

请求头中：Authorization 为 "Basic" 加上 clientid:secret 经过base64编码后的字符串，比如可以这样获得 `System.out.println(new Base64().encodeAsString("my-client-1:12345678".getBytes()));`

```http request
POST /oauth/token?grant_type=authorization_code&amp; scope=all HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded
Authorization: Basic bXktY2xpZW50LTE6MTIzNDU2Nzg=
cache-control: no-cache
code=3CRpVM
redirect_uri=http%3A%2F%2Flocalhost%3A9090%2Fcallback
```

响应中将会携带 token 信息

### oauth2中的密码模式

1. 发送请求，携带用户名和密码，以获取token，请求如下：

```http request
POST /oauth/token?grant_type=password&amp; scope=all HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded
Authorization: Basic bXktY2xpZW50LTE6MTIzNDU2Nzg=
cache-control: no-cache
username=admin
password=12345678
```

响应中将会携带 token 信息

## 如何携带 token 访问受限资源

在请求头中包含 Authorization 字段，其格式为 "Bearer" + 空格 + token值

```http request
GET /user/list HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTEyMDU1MzQsInVzZXJfbmFtZSI6InpoYW5nc2FuIiwiYXV0aG9yaXRpZXMiOlsiVXNlckluZGV4IiwiQm9va0FkZCIsIkJvb2tEZXRhaWwiLCJVc2VyTGlzdCJdLCJqdGkiOiIyMDQ5NzAzZi0xZTY2LTQ0ZWQtYTkxZS0xZmFjYTBhNDkyZWQiLCJjbGllbnRfaWQiOiJteS1jbGllbnQtMSIsInNjb3BlIjpbImFsbCJdfQ.IhuWGLmuOVfhlej1XUtijFo260I3Q4o_CiOUeJ4ZUks
cache-control: no-cache
```
