package com.juejin.usercenter.model.vo.home;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;


@Data
public class HomeUserListVO implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 列表
     */
    private ArrayList<UserListVO> list;

    private static final long serialVersionUID = 4603111303657020130L;
}
