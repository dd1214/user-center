package com.juejin.usercenter.model.vo.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserVO implements Serializable  {

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 简介
     */
    private String introduction;

    private static final long serialVersionUID = 4603111303657020130L;

}
