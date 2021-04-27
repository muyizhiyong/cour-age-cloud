package com.muyi.courage.gate.authorization;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * 
 */
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";
    private static final String AUTHORITY_PREFIX = "ROLE_";

    @Resource
    RedisTemplate<String, Object> gateRedisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {

        log.info("___________________AuthorizationManager____________________________");

        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        Object obj = gateRedisTemplate.opsForHash().get(RESOURCE_ROLES_MAP, uri.getPath());
        List<String> authorities = Convert.toList(String.class,obj);
        authorities = authorities.stream().map(i -> i = AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
//        ServerWebExchange exchange = authorizationContext.getExchange();
//        //请求资源
//        String requestPath = exchange.getRequest().getURI().getPath();
////
//        return mono.map(auth -> {
//            return new AuthorizationDecision(checkAuthorities(exchange, auth, requestPath));
//        }).defaultIfEmpty(new AuthorizationDecision(false));
    }
    //权限校验
//    private boolean checkAuthorities(ServerWebExchange exchange, Authentication auth, String requestPath) {
//        if(auth instanceof OAuth2Authentication){
//            OAuth2Authentication athentication = (OAuth2Authentication) auth;
//            String clientId = athentication.getOAuth2Request().getClientId();
//            log.info("clientId is {}",clientId);
//        }
//
//        Object principal = auth.getPrincipal();
//        log.info("用户信息:{}",principal.toString());
//        return true;
//    }
}
