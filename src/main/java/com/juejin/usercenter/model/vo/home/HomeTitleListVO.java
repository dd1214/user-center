package com.juejin.usercenter.model.vo.home;

import lombok.Data;

import java.io.Serializable;

@Data
public class HomeTitleListVO implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 链接
     */
    private String url;

    /**
     * 标记
     */
    private String badge;

    /**
     * 是否使用图片
     */
    private boolean isImage;

    /**
     * 图片链接
     */
    private String imgUrl;

    private static final long serialVersionUID = 4603111303657020130L;
}
