package com.muyi.courage.auth.web;

import com.muyi.courage.auth.dto.LoginResultDTO;
import com.muyi.courage.auth.dto.UserDTO;
import com.muyi.courage.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨志勇
 * @date 2020-09-23 20:16
 */
@Api(value = "权限模块：系统登录接口", tags = {"权限模块：系统接口"})
public interface LoginResource {

    String PREFIX = "/auth";

    @PostMapping(PREFIX+"/login")
    @ApiOperation(value = "登录", notes = "登录")
    LoginResultDTO authLogin(@RequestBody UserDTO user);


    @PostMapping(PREFIX+"/refresh")
    @ApiOperation(value = "刷新", notes = "刷新")
    LoginResultDTO authRefresh(@RequestBody String refreshToken);


    @PostMapping(PREFIX+"/loginout")
    @ApiOperation(value = "PC端，登出", notes = "PC端，登出")
    DTO authLoginOut(HttpServletRequest request);

}
