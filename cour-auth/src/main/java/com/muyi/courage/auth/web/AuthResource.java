package com.muyi.courage.auth.web;

import com.muyi.courage.auth.api.CommonResult;
import com.muyi.courage.auth.dto.Oauth2TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * @author 杨志勇
 * @date 2021-04-27 14:19
 */
@Api(value = "权限模块：token", tags = {"权限模块：token"})
public interface AuthResource {

    String PREFIX = "/auth";

    @PostMapping(PREFIX+"/postAccessToken")
    @ApiOperation(value = "登录", notes = "登录")
    CommonResult<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException;
}
