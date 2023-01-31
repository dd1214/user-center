package com.juejin.usercenter.model.dto.article;


import com.juejin.usercenter.model.vo.article.UpdateArticleVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateArticleRequest implements Serializable {

    private String id;
    /**
     * 内容
     */

    private UpdateArticleVO content;

    private static final long serialVersionUID = 4603111303657020130L;
}
