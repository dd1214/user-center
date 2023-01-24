package com.juejin.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.service.ArticleService;
import com.juejin.usercenter.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.juejin.usercenter.contant.ArticleConstant.ARTICLE_CATEGORY;
import static com.juejin.usercenter.contant.UserConstant.USER_LOGIN_STATE;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Override
    public Integer articleAdd(String title, String snapshoot, String preview, String category, String content, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请先登录");
        }
        if (currentUser.getUserrole() != 1){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (title.length() > 100 || title.length() < 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题长度不符合要求");
        }
        if (preview.length() > 200 || preview.length() < 25){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"预览长度不符合要求");
        }
        if (content.length() > 100000 || content.length() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"内容长度不符合要求");
        }
        int flag = 0;
        for (String s : ARTICLE_CATEGORY) {
            if (s.equals(category)) {
                flag++;
            }
        }
        if (flag != 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标签内容不合法");
        }
        String articleId = String.valueOf(System.currentTimeMillis() + (int)((Math.random()*9+1)*100000));
        Article article = new Article();
        article.setArticleid(articleId);
        article.setTitle(title);
        article.setSnapshoot(snapshoot);
        article.setPreview(preview);
        article.setCategory(category);
        article.setContent(content);
        boolean save = this.save(article);
        if (save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"文章发布失败");
        }
        return 1;
    }
}




