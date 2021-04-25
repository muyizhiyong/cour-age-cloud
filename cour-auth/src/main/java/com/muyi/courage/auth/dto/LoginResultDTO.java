package com.muyi.courage.auth.dto;

import com.muyi.courage.common.dto.DTO;
import com.muyi.courage.common.util.RetCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 杨志勇
 * @date 2020-09-23 20:19
 */
@Getter
@Setter
public class LoginResultDTO extends DTO {
    private String access_token;
    private String expires_in;
    private String jti;
    private String refresh_token;
    private String scope;
    private String token_type;

    public LoginResultDTO() {
    }

    public LoginResultDTO(RetCodeEnum re) {
        super(re);
    }

}
