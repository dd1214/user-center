package com.juejin.usercenter.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户 id
     */
    @TableId
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
     * 密码
     */
    private String userPassword;

    /**
     * 创作等级
     */
    private String creationLevel;

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
     * 用户状态 0-正常 1-冻结
     */
    private Integer userStatus;

    /**
     * 用户权限 0-用户 1-管理员
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    private Date currentTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除 0-正常 1-删除
     */
    private Integer isDelete;

    /**
     * 账号
     */
    private String userAccount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}