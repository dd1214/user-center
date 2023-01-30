package com.juejin.usercenter.model.dto.article;

import com.juejin.usercenter.model.vo.ArticleVO;
import com.juejin.usercenter.model.vo.ImportArticleVO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class ImportArticleRequest implements Serializable {

    /**
     * 内容
     */

    private ArrayList<ImportArticleVO> content;

    private static final long serialVersionUID = 4603111303657020130L;
}
