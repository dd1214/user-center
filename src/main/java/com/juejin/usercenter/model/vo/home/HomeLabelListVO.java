package com.juejin.usercenter.model.vo.home;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class HomeLabelListVO implements Serializable {

    /**
     * 展示文字
     */
    private String text;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 标记
     */
    private String badge;

    /**
     * 子序列
     */
    private ArrayList<HomeSubListVO> sublist;

    private static final long serialVersionUID = 4603111303657020130L;
}
