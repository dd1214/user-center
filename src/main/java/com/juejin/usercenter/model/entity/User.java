package com.juejin.usercenter.model.entity;

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
    @TableId(value = "userID")
    private String userid;

    /**
     * 用户昵称
     */
    @TableField(value = "nickName")
    private String nickname;

    /**
     * 用户头像
     */
    @TableField(value = "userAvatar")
    private String useravatar;

    /**
     * 密码
     */
    @TableField(value = "userPassword")
    private String userpassword;

    /**
     * 创作等级
     */
    @TableField(value = "creationLevel")
    private String creationlevel;

    /**
     * 简介
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 点赞数
     */
    @TableField(value = "likesNumber")
    private Integer likesnumber;

    /**
     * 阅读量
     */
    @TableField(value = "readingQuantity")
    private Integer readingquantity;

    /**
     * 用户状态 0-正常 1-冻结
     */
    @TableField(value = "userStatus")
    private Integer userstatus;

    /**
     * 用户权限 0-用户 1-管理员
     */
    @TableField(value = "userRole")
    private Integer userrole;

    /**
     * 创建时间
     */
    @TableField(value = "currentTime")
    private Date currenttime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updatetime;

    /**
     * 逻辑删除 0-正常 1-删除
     */
    @TableField(value = "isDelete")
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}