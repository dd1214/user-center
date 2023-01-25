package com.juejin.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juejin.usercenter.common.BaseResponse;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.common.ResultUtils;
import com.juejin.usercenter.constant.CommonConstant;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.model.dto.article.ArticleAddRequest;
import com.juejin.usercenter.model.dto.article.CurrentArticleRequest;
import com.juejin.usercenter.model.dto.article.CurrentListArticle;
import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.ArticleVO;
import com.juejin.usercenter.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文档接口
 *
 * @author 梁钊炜
 */

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 发布文章
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
        return ResultUtils.success(articleService.articleAdd(title,snapshoot,preview,category,content,request));
    }

    /**
     * 获取一条文章
     * @param currentArticleRequest 请求
     * @return 文章映射
     */

    @PostMapping("/current")
    public BaseResponse<ArticleVO> currentArticle(@RequestBody CurrentArticleRequest currentArticleRequest){
        if (currentArticleRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String id = currentArticleRequest.getId();
        if (StringUtils.isAnyBlank(id)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(articleService.currentArticle(id));
    }

    @PostMapping("current_list")
    public BaseResponse<ArrayList<ArticleVO>> currentListArticle(@RequestBody CurrentListArticle currentListArticle){
        if (currentListArticle == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long current = currentListArticle.getCurrent();
        long size = currentListArticle.getPageSize();
        String sortField = currentListArticle.getSortField();
        String sortOrder = currentListArticle.getSortOrder();
        String category = currentListArticle.getCategory();
        //限制爬虫
        if(size > 50){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(articleService.currentListArticle(current,size,sortField,sortOrder,category));

    }
}
