package com.juejin.usercenter.model.entity;

import com.baomidou.mybatisplus.annotation.*;

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
    @TableId
    private String articleID;

    /**
     * 创建人
     */
    private String author;

    /**
     * 快照
     */
    private String snapshot;

    /**
     * 文章题目
     */
    private String title;

    /**
     * 预览
     */
    private String preview;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 标签
     */
    private String label;

    /**
     * 点赞数
     */
    private Integer collectCount;

    /**
     * 阅读量
     */
    private Integer viewCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 分类
     */
    private String category;

    /**
     * 文章状态 0-草稿箱 1-待审核 2-已发布
     */
    private Integer articleStatus;

    /**
     * 创建时间
     */
    private Date currentTime;

    /**
     * 更新时间
     */
    private Date upDatetime;

    /**
     * 逻辑删除 0-正常 1-异常
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}