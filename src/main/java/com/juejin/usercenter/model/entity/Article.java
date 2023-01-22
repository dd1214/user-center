package com.juejin.usercenter.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article implements Serializable {
    /**
     * 文章id
     */
    @TableId(value = "articleID")
    private String articleid;

    /**
     * 创建人
     */
    @TableField(value = "createUser")
    private String createuser;

    /**
     * 快照
     */
    @TableField(value = "snapshoot")
    private String snapshoot;

    /**
     * 文章题目
     */
    @TableField(value = "title")
    private String title;

    /**
     * 预览
     */
    @TableField(value = "preview")
    private String preview;

    /**
     * 文章内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 标签
     */
    @TableField(value = "label")
    private String label;

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
     * 评论数
     */
    @TableField(value = "commentCount")
    private Integer commentcount;

    /**
     * 分类
     */
    @TableField(value = "category")
    private String category;

    /**
     * 文章状态 0-未发布 1-正常
     */
    @TableField(value = "articleStatus")
    private Integer articlestatus;

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
     * 逻辑删除 0-正常 1-异常
     */
    @TableField(value = "isDelete")
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}