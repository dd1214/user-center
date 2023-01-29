package com.juejin.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class ArticleVO implements Serializable {

    /**
     * 文章id
     */
    private String article_id;

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
    private int view_count;

    /**
     * 点赞数
     */
    private int collect_count;

    /**
     *  评论数
     */
    private int comment_count;

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
     * 状态
     */
    private int state;

    private static final long serialVersionUID = 4603111303657020130L;
}
