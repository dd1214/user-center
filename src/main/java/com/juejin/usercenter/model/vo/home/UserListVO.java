package com.juejin.usercenter.model.vo.home;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserListVO implements Serializable {

    /**
     * 用户 id
     */
    private String userId;

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

    /**
     * 创作等级
     */

    private String creationLevel;

    private static final long serialVersionUID = 1L;
}
