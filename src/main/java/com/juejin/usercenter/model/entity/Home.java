package com.juejin.usercenter.model.entity;

import com.juejin.usercenter.model.vo.home.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class Home implements Serializable {

    private ArrayList<HomeTitleListVO> titleList;

    private ArrayList<HomeLabelListVO> labelList;

    private ArrayList<ScreeningListVO> screening;

    private ArrayList<AdvertisementVO> advertisement;

    private ArrayList<HomeUserListVO> userList;

    private static final long serialVersionUID = 4603111303657020130L;
}
