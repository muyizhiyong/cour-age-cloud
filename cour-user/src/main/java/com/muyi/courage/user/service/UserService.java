package com.muyi.courage.user.service;

import com.muyi.courage.common.dto.DTO;
import com.muyi.courage.user.dto.UserDTO;

/**
 * @author 杨志勇
 * @date 2021-01-15 13:53
 */
public interface UserService {
    DTO addUser(UserDTO userDTO);

    UserDTO qryByName(String name);
}
