package com.juejin.usercenter.model.dto.article;


import com.juejin.usercenter.model.vo.ArticleVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteArticleRequest implements Serializable {

    /**
     * id
     */

    private String id;

    private static final long serialVersionUID = 4603111303657020130L;

}
