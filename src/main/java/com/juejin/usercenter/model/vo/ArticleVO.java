package com.juejin.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 前端文章获取类
 */


@Data
public class ArticleVO implements Serializable {

    /**
     * 文章id
     */
    private String articleID;

    /**
     * 快照
     */
    private String snapshot;

    /**
     * 标题
     */
    private String title;

    /**
     * 预览
     */
    private String preview;

    /**
     * 作者
     */
    private String author;

    /**
     * 阅读数
     */
    private int viewCount;

    /**
     * 点赞数
     */
    private int collectCount;

    /**
     *  评论数
     */
    private int commentCount;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 分类
     */
    private String category;

    /**
     * 内容
     */
    private String content;

    /**
     * 文章状态
     */
    private int articleStatus;

    /**
     * 创建时间
     */
    private Date currentTime;

    private static final long serialVersionUID = 4603111303657020130L;
}
