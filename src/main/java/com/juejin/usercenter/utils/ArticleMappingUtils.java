package com.juejin.usercenter.utils;


import com.fasterxml.jackson.databind.util.BeanUtil;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.exception.BusinessException;

import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.ArticleVO;
import org.springframework.beans.BeanUtils;

/**
 * 文章映射工具类
 */

public class ArticleMappingUtils {

    public static ArticleVO articleMapping(Article article , User user){
        if (article == null || user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article,articleVO);
        articleVO.setAuthor(user.getNickname());
        articleVO.setAvatar(user.getUseravatar());
        return articleVO;
    }
}
