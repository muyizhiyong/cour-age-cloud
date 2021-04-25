package com.muyi.courage.auth.security.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security Oauth2 资源服务配置类
 * <p>
 * {@link ResourceServerConfigurer}，内部关联了{@link ResourceServerSecurityConfigurer}和{@link HttpSecurity}
 * {@link ResourceServerSecurityConfigurer}
 * --创建{@link OAuth2AuthenticationProcessingFilter}，即OAuth2核心过滤器
 * ----获取access_token并填入security context
 * --且为{@link OAuth2AuthenticationProcessingFilter}提供了{@link AuthenticationManager}实现类
 * ----即{@link OAuth2AuthenticationManager}
 * -------内部维护了{@link ClientDetailsService}和{@link ResourceServerTokenServices}
 * ---------调用这个tokenServices来加载、读取token
 * --设置{@link TokenExtractor}默认的实现{@link BearerTokenExtractor}
 * ----分离出请求中包含的token。可以使用多种方式携带token，header，uri参数，表单
 * --相关的异常处理器，可以重写相关实现来自定义异常
 * ----重写的异常handler，在{@link EnableResourceServer}配置类中被覆盖
 *
 */
@Configuration
@EnableResourceServer
public class Oauth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	/**
	 * 权限管理分为两部分，此方法包括认证设置  <br>
	 * 第一步登录认证, 可以是此处的拦截器放行（ExpressionBasedFilterInvocationSecurityMetadataSource） <br>
	 * 第二步访问鉴权, <资源,角色> <br>
	 * 此处通过authorizeRequests()方法来开始请求权限配置。 <br>
	 * 而接着的.anyRequest().authenticated()是对http所有的请求必须通过授权认证才可以访问。 <br>
	 * <p>
	 * PS：callback登录接口不能带Authorization头，否则进不去Controller，原因是[认证]和[鉴权]是进入不同的过滤器链
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
				.authorizeRequests()
				.antMatchers("/", "/swagger-ui.html", "/v2/**", "/webjars/**", "/doc.html/**", "/swagger-resources/**").permitAll()
				.antMatchers("/auth/**").permitAll()
				.antMatchers("/oauth/**").permitAll()
				.antMatchers("/push-module/websocket").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.anyRequest().authenticated()

				.and()
				// 可以指定在哪个HttpServletRequest实例上调用此配置
				.requestMatchers()
				.antMatchers("/", "/swagger-ui.html", "/v2/**", "/webjars/**", "/swagger-resources/**")
				.antMatchers("/auth/**")
				.antMatchers("/oauth/**")
				.antMatchers("push-module/websocket")
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.anyRequest()

				.and()
				.cors()
				.and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.httpBasic();

	}


	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		super.configure(resources);
	}

	@Bean
    CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
