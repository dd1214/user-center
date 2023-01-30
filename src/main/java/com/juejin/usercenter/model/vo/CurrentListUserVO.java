package com.juejin.usercenter.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class CurrentListUserVO implements Serializable {

    /**
     * 数据列表
     */

    private ArrayList<UserVO> list;

    /**
     * 数据总条数
     */

    private long total;

    private static final long serialVersionUID = 4603111303657020130L;

}
