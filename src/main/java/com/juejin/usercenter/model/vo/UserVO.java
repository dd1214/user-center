package com.juejin.usercenter.model.vo;

import lombok.Data;

import java.util.Date;


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
    private String avatar;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 点赞数
     */
    private Integer collectCount;

    /**
     * 阅读量
     */
    private Integer viewCount;

    /**
     * 创建时间
     */

    private Date currentTime;

    private static final long serialVersionUID = 1L;
}
