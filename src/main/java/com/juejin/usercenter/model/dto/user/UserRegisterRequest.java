package com.juejin.usercenter.model.dto.user;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserRegisterRequest implements Serializable{

    /**
     * 昵称
     */

    private String userAccount;

    /**
     * 昵称
     */

    private String nickname;

    /**
     * 用户头像
     */

    private String userAvatar;

    /**
     * 密码
     */

    private String userPassword;

    /**
     * 简介
     */

    private String introduction;


    private static final long serialVersionUID = 4603111303657020130L;


}
