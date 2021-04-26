package com.muyi.courage.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {


	@Bean
	public Docket api1() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName("权限模块")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.muyi.courage.auth"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts())
				;
	}



	private List<ApiKey> securitySchemes() {
		List<ApiKey> list = new ArrayList<>();
		list.add(new ApiKey("Authorization", "Authorization", "header"));
		return list;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> list = new ArrayList<>();
		SecurityContext build = SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$"))
				.build();
		list.add(build);
		return list;
	}

	private List<SecurityReference> defaultAuth() {

		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
		SecurityReference authorization = new SecurityReference("Authorization", authorizationScopes);

		List<SecurityReference> list = new ArrayList<>();
		list.add(authorization);
		return list;
	}

}
