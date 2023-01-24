package com.juejin.usercenter.controller;


import com.juejin.usercenter.common.BaseResponse;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.common.ResultUtils;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.model.dto.article.ArticleAddRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 文档接口
 *
 * @author 梁钊炜
 */

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    /**
     *
     * @param articleAddRequest 添加文档
     * @return 返回插入行数
     */

    @PostMapping("/add")
    public BaseResponse<Integer> articleAdd(@RequestBody ArticleAddRequest articleAddRequest, HttpServletRequest request){
        if (articleAddRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = articleAddRequest.getTitle();
        String snapshoot = articleAddRequest.getSnapshoot();
        String preview = articleAddRequest.getPreview();
        String category = articleAddRequest.getCategory();
        String content = articleAddRequest.getContent();
        if (StringUtils.isAnyBlank(title,preview,content,category)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(1);
    }
}
