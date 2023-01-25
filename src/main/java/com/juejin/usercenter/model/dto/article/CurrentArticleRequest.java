package com.juejin.usercenter.model.dto.article;


import lombok.Data;

import java.io.Serializable;

@Data
public class CurrentArticleRequest implements Serializable {

    /**
     * 文章id
     */

    private String id;

    private static final long serialVersionUID = 4603111303657020130L;

}
