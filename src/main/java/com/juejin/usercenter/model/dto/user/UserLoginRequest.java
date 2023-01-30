package com.juejin.usercenter.model.dto.user;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    /**
     * 昵称
     */
    private String userAccount;

    /**
     * 密码
     */

    private String userPassword;

    private static final long serialVersionUID = 4603111303657020130L;
}
