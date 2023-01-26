package com.juejin.usercenter.model.dto.article;

import com.juejin.usercenter.model.vo.ArticleVO;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateArticleRequest implements Serializable {

    /**
     * 内容
     */

    private ArticleVO content;

    private static final long serialVersionUID = 4603111303657020130L;
}
