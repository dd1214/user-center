package com.juejin.usercenter.model.vo.home;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdvertisementVO implements Serializable {
    /**
     * 图片链接
     */
    private String imgUrl;

    /**
     * 跳转链接
     */
    private String url;

    /**
     * 是否开启
     */
    private boolean isOpen;

    private static final long serialVersionUID = 4603111303657020130L;
}
