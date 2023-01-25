package com.juejin.usercenter.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.mapper.UserMapper;
import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.ArticleVO;

/**
 * 文章映射工具类
 */

public class ArticleMappingUtils {

    public static ArticleVO articleMapping(Article article , User user){
        if (article == null || user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleVO articleVO = new ArticleVO();
        articleVO.setArticle_id(article.getArticleid());
        articleVO.setSnapshot(article.getSnapshoot());
        articleVO.setTitle(article.getTitle());
        articleVO.setPreview(article.getPreview());
        articleVO.setAuthor(user.getNickname());
        articleVO.setView_count(article.getReadingquantity());
        articleVO.setCollect_count(article.getLikesnumber());
        articleVO.setComment_count(article.getCommentcount());
        articleVO.setAvatar(user.getUseravatar());
        articleVO.setCategory(article.getCategory());
        articleVO.setContent(article.getContent());
        return articleVO;
    }
}
