package com.juejin.usercenter.model.dto.article;

import com.juejin.usercenter.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CurrentListUserRequest extends PageRequest implements Serializable {

    private String userAccount;

    private String content;

    private static final long serialVersionUID = 4603111303657020130L;

}
