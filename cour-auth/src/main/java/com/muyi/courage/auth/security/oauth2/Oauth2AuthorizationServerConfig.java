package com.muyi.courage.auth.security.oauth2;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Spring Security Oauth2 登录认证配置类
 * <p>
 * 顶级身份管理者: {@link AuthenticationManager}
 * 用来从请求中获取client_id,client_secret，组装成一个UsernamePasswordAuthenticationToken作为身份标识
 * --其实现类是 {@link ProviderManager}
 * ----内部维护的Provider数组中{@link DaoAuthenticationProvider}
 * ------内置了{@link UserDetailsService}接口实现，它是获取用户详细信息的最终接口
 * <p>
 * 经过前置校验和身份封装之后，便到达了{@link TokenEndpoint}
 * --其内部依赖{@link TokenGranter} 来颁发token, 包含5种授权模式
 * ----其抽象类中 {@link AbstractTokenGranter}
 * ------通过{@link AuthorizationServerTokenServices}来创建、刷新、获取token
 * --------其默认的实现类{@link DefaultTokenServices}，会调用tokenStore对创建的token和相关信息存储到对应的实现类中
 */
@EnableAuthorizationServer
@Configuration
class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${auth.accessTokenValiditySeconds}")
	private String accessTokenValiditySeconds;
	@Value("${auth.refreshTokenValiditySeconds}")
	private String refreshTokenValiditySeconds;

	@Value("${redis.prefix}")
	private String redisPrefix;

	private final AuthenticationManager authenticationManager;

	private final UserDetailsService userDetailsService;

	private final RedisTemplate authAdminRedisTemplate;

	@Autowired
	public Oauth2AuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration, UserDetailsService userDetailsService, RedisTemplate authAdminRedisTemplate) throws Exception {
		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
		this.userDetailsService = userDetailsService;
		this.authAdminRedisTemplate = authAdminRedisTemplate;
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()

				.withClient("pc-web")
				.secret("$2a$10$0jyHr4rGRdQw.X9mrLkVROdQI8.qnWJ1Sl8ly.yzK0bp06aaAkL9W")
				.authorizedGrantTypes("authorization_code", "password", "refresh_token")
				.accessTokenValiditySeconds(Integer.parseInt(accessTokenValiditySeconds))
				.refreshTokenValiditySeconds(Integer.parseInt(refreshTokenValiditySeconds))
				.scopes("all")
				.redirectUris("http://localhost:18888/callback")
				;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//super.configure(security);
		security
				.allowFormAuthenticationForClients();
	}



	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.userDetailsService(userDetailsService)
				.tokenStore(redisTokenStore())
				.accessTokenConverter(accessTokenConverter())
				.authenticationManager(authenticationManager)
				.reuseRefreshTokens(false);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("secret");
		return converter;
	}

	@Bean("redisTokenStore")
	public TokenStore redisTokenStore() {
		RedisTokenStore redisTokenStore = new RedisTokenStore(authAdminRedisTemplate.getRequiredConnectionFactory());
		if (redisPrefix != null && !"".equals(redisPrefix)) {
			redisTokenStore.setPrefix(redisPrefix);
		}
		return redisTokenStore;
	}

	/**
	 * 头部部分 {
	 * “alg”: “HS256”, 签名算法
	 * “typ”: “JWT”
	 * }
	 * <p>
	 * 载荷部分 {
	 * “iss”: “发行者”,
	 * “sub”: 主题”,
	 * “aud”: “观众”，
	 * “exp”: ”过期时间”,
	 * “自定义数据”:”自定义数据”
	 * }
	 * <p>
	 * 签名部分 [32 crypto bytes]
	 * 使用 HS256(Base64(Header) + “.” + Base64(Payload), secret) 判断是否被篡改
	 */
	public static void main(String[] args) {
		System.out.println(new Base64().encodeAsString("pc-web:12345678".getBytes()));
		System.out.println(java.util.Base64.getEncoder().encodeToString("my-client-1:12345678".getBytes()));
//		pc-web:12345678       -->  cGMtd2ViOjEyMzQ1Njc4
//		my-client-1:12345678  -->  bXktY2xpZW50LTE6MTIzNDU2Nzg=
//		mobile:12345678 	  -->  bW9iaWxlOjEyMzQ1Njc4

		Jwt decode = JwtHelper.decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTEzNjYxMTIsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJlNTk4MTU4ZS00OWJlLTQwNjgtYTdiNi02NDcwNDE5Y2E3NDAiLCJjbGllbnRfaWQiOiJteS1jbGllbnQtMSIsInNjb3BlIjpbImFsbCJdfQ.fZqMGddQM00gaUAJKtb4ly4sXAhiiGPCjvM65y2sYAc");
		System.out.println(decode);
//		{"alg":"HS256","typ":"JWT"} {"exp":1551366112,"user_name":"admin","authorities":["ROLE_ADMIN"],"jti":"e598158e-49be-4068-a7b6-6470419ca740","client_id":"my-client-1","scope":["all"]} [32 crypto bytes]

		boolean secret = new BCryptPasswordEncoder().matches("12345678", "$2a$10$0jyHr4rGRdQw.X9mrLkVROdQI8.qnWJ1Sl8ly.yzK0bp06aaAkL9W");
		System.out.println(secret);
// 		true

	}
}
