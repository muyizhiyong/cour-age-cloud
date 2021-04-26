package com.muyi.courage.auth.service.impl;

import com.muyi.courage.common.dto.DTO;
import com.muyi.courage.auth.dto.UserDTO;
import com.muyi.courage.auth.po.SysUserPO;
import com.muyi.courage.auth.repository.SysUserMapper;
import com.muyi.courage.auth.service.LoginService;
import com.muyi.courage.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 杨志勇
 * @date 2020-09-25 14:34
 */
@Slf4j
@Service
public class LoginSericeImpl implements LoginService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public DTO checkUser(UserDTO user) {
        DTO dto = new DTO(RetCodeEnum.FAIL);
        String userName = user.getUserName();
        String password = user.getPassword();
        log.debug("[checkUser] userName:"+userName);
        log.debug("[checkUser] password:"+password);
        SysUserPO sysUserPO = sysUserMapper.selectByPrimaryKey(userName);
        if (sysUserPO == null) {
            dto.setRetMsg("用户名不正确！");
            return dto;
        }
        if (!passwordEncoder.matches(password, sysUserPO.getPassword())) {
            dto.setRetMsg("密码不正确！");
            return dto;
        }
        if (sysUserPO.getStatus() == 0) {
            dto.setRetMsg("用户已停用！");
            return dto;
        }

        dto.setResult(RetCodeEnum.SUCCEED);
        return dto;
    }
}
