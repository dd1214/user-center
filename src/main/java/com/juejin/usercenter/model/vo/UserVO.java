package com.juejin.usercenter.model.vo;

import lombok.Data;


@Data
public class UserVO {
    /**
     * 用户 id
     */
    private String userid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String useravatar;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 点赞数
     */
    private Integer likesnumber;

    /**
     * 阅读量
     */
    private Integer readingquantity;

    private static final long serialVersionUID = 1L;
}
