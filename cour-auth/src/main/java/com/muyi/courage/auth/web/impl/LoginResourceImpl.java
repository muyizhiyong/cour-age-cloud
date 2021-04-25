package com.muyi.courage.auth.web.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.muyi.courage.auth.dto.LoginResultDTO;
import com.muyi.courage.auth.dto.UserDTO;
import com.muyi.courage.auth.service.LoginService;
import com.muyi.courage.auth.web.LoginResource;
import com.muyi.courage.common.dto.DTO;
import com.muyi.courage.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * @author 杨志勇
 * @date 2020-09-24 09:06
 */
@Slf4j
@RestController
public class LoginResourceImpl implements LoginResource {

    private static String LOCAL_PORT = "18888";

    @Value("${server.port}")
    private String port;

    @PostConstruct
    public void init(){
        LOCAL_PORT = port;
    }

    @Resource
    private LoginService loginService;

    @Resource
    ConsumerTokenServices tokenServices;

    @Override
    public LoginResultDTO authLogin(UserDTO user) {
        LoginResultDTO loginResultDTO = new LoginResultDTO(RetCodeEnum.FAIL);

        DTO dto = loginService.checkUser(user);
        if (RetCodeEnum.FAIL.getCode().equals(dto.getRetCode())) {
            loginResultDTO.setRetMsg(dto.getRetMsg());
            return loginResultDTO;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        //String clientAuth="Basic YWRtaW46MTIzNDU2Nzg=";
        //headers.add("Authorization", clientAuth);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", user.getUserName());
        params.add("password", user.getPassword());

        String clientId = "pc-web";
        params.add("client_id", clientId);
        params.add("client_secret", user.getPassword());

        params.add("scopes", "all");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:" + LOCAL_PORT.trim() + "/oauth/token", requestEntity, String.class);
        log.debug("[callback response]: {}", response);
        return createLoginResultDTO(response);
    }

    @Override
    public LoginResultDTO authRefresh(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String clientAuth="Basic YWRtaW46MTIzNDU2Nzg=";
        headers.add("Authorization", clientAuth);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);

        //String clientId = "pc-web";
        //params.add("client_id", clientId);
        //params.add("client_secret", "12345678");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://127.0.0.1:" + LOCAL_PORT.trim() + "/oauth/token", requestEntity, String.class);
        log.debug("[callback response]: {}", response);
        return createLoginResultDTO(response);
    }

    @Override
    public DTO authLoginOut(HttpServletRequest request) {
        String accessToken=request.getHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring("Bearer ".length());
        }
        log.debug("[authLoginOut] accessToken :"+accessToken);
        if (tokenServices.revokeToken(accessToken)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                log.debug("Invalidating session: {}", session.getId());
                session.invalidate();
            }
            SecurityContext context = SecurityContextHolder.getContext();
            if (context != null) {
                context.setAuthentication(null);
            }
            SecurityContextHolder.clearContext();
            return new DTO(RetCodeEnum.SUCCEED);
        } else {
            return new DTO(RetCodeEnum.FAIL);
        }
    }


    private LoginResultDTO createLoginResultDTO(ResponseEntity<String> response) {
        JSONObject jsonObject = JSON.parseObject(response.getBody());

        LoginResultDTO loginResultDTO = new LoginResultDTO(RetCodeEnum.SUCCEED);
        loginResultDTO.setAccess_token(jsonObject.getString("access_token"));
        loginResultDTO.setExpires_in(jsonObject.getString("expires_in"));
        loginResultDTO.setJti(jsonObject.getString("jti"));
        loginResultDTO.setRefresh_token(jsonObject.getString("refresh_token"));
        loginResultDTO.setScope(jsonObject.getString("scope"));
        loginResultDTO.setToken_type(jsonObject.getString("token_type"));

        return loginResultDTO;
    }
}
