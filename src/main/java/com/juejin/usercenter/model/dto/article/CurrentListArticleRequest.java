package com.juejin.usercenter.model.dto.article;

import com.juejin.usercenter.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CurrentListArticleRequest extends PageRequest implements Serializable {

    private Integer articleStatus;

    private String label;

    private String content;

    private String category;

    private static final long serialVersionUID = 4603111303657020130L;

}
