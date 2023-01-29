package com.juejin.usercenter.model.dto.home;

import lombok.Data;

import java.io.Serializable;

@Data
public class TitleList implements Serializable {

    private String titleName;

    private String badge;

    private String link;

    private boolean isDefault;

    private static final long serialVersionUID = 4603111303657020130L;
}
