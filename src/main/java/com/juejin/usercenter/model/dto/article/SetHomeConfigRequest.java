package com.juejin.usercenter.model.dto.article;

import com.juejin.usercenter.model.entity.Home;
import lombok.Data;

import java.io.Serializable;

@Data
public class SetHomeConfigRequest implements Serializable {

    /**
     * 主页配置
     */

    private Home homeConfig;

    private static final long serialVersionUID = 4603111303657020130L;

}
