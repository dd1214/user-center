package com.juejin.usercenter.model.dto.user;

import com.juejin.usercenter.model.vo.UpdateArticleVO;
import com.juejin.usercenter.model.vo.UpdateUserVO;
import lombok.Data;

import java.io.Serializable;


@Data
public class UpdateUserRequest implements Serializable {

    private String id;
    /**
     * 内容
     */

    private UpdateUserVO content;

    private static final long serialVersionUID = 4603111303657020130L;
}
