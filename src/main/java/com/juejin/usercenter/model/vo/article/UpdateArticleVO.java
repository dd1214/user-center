package com.juejin.usercenter.model.vo.article;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateArticleVO implements Serializable {

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
     * 分类
     */
    private String category;

    /**
     * 内容
     */
    private String content;

    private static final long serialVersionUID = 4603111303657020130L;


}
