package com.juejin.usercenter.model.dto.article;

import com.juejin.usercenter.model.vo.article.AuditArticleVO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;


@Data
public class UpdateStatusRequest implements Serializable {

    /**
     * 列表
     */

    private ArrayList<AuditArticleVO> content;


    private static final long serialVersionUID = 4603111303657020130L;


}
