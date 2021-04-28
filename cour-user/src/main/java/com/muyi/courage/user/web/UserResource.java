package com.muyi.courage.user.web;

import com.muyi.courage.common.dto.DTO;
import com.muyi.courage.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 杨志勇
 * @date 2021-01-13 22:22
 */
@Api(value = "用户模块：用户管理接口", tags = {"用户模块：用户管理接口"})
public interface UserResource {
    String PREFIX = "/user";

    @PostMapping(PREFIX)
    @ApiOperation(value = "添加用户", notes = "添加用户")
    DTO addUser(@RequestBody UserDTO userDTO);

    @GetMapping(PREFIX+"/qyrByName")
    @ApiOperation(value = "查询用户", notes = "查询用户")
    UserDTO qryByName(@RequestParam String name);
}
