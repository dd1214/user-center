package com.juejin.usercenter.model.vo.home;

import lombok.Data;

import java.io.Serializable;


@Data
public class ScreeningListVO implements Serializable {

    /**
     * 展示文字
     */
    private String text;

    /**
     * 参数
     */
    private String parameter;

    private static final long serialVersionUID = 4603111303657020130L;

}
