package com.juejin.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.constant.CommonConstant;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.mapper.UserMapper;
import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.ArticleVO;
import com.juejin.usercenter.service.ArticleService;
import com.juejin.usercenter.mapper.ArticleMapper;
import com.juejin.usercenter.utils.ArticleMappingUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.juejin.usercenter.constant.ArticleConstant.ARTICLE_CATEGORY;
import static com.juejin.usercenter.constant.UserConstant.USER_LOGIN_STATE;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 添加文章
     * @param title 标题
     * @param snapshoot 快照
     * @param preview  预览
     * @param category 分类
     * @param content  内容
     * @param request 请求
     * @return 成功
     */

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
        String articleId = String.valueOf(System.currentTimeMillis()) +  (int)((Math.random()*9+1)*100000);
        Article article = new Article();
        article.setArticleid(articleId);
        article.setCreateuser(currentUser.getUserid());
        article.setTitle(title);
        article.setSnapshoot(snapshoot);
        article.setPreview(preview);
        article.setCategory(category);
        article.setContent(content);
        boolean save = this.save(article);
        if (!save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"文章发布失败");
        }
        return 1;
    }

    /**
     * 获取文章
     * @param id id
     * @return 文章封装类
     */

    @Override
    public ArticleVO currentArticle(String id) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("articleID",id);
        Article article = articleMapper.selectOne(articleQueryWrapper);
        if (article == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"文章不存在");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",article.getCreateuser());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"作者信息获取失败");
        }
        return ArticleMappingUtils.articleMapping(article,user);
    }

    @Override
    public ArrayList<ArticleVO> currentListArticle(long current, long size, String sortField, String sortOrder, String category) {
        ArrayList<ArticleVO> articles = new ArrayList<>();
        try {
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.eq(StringUtils.isNotBlank(category),"category",category);
            articleQueryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                    sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortField);
            Page<Article> page = articleMapper.selectPage(new Page<>(current, size), articleQueryWrapper);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            for (Article record : page.getRecords()) {
                queryWrapper.eq("userID",record.getCreateuser());
                User user = userMapper.selectOne(queryWrapper);
                if (user == null){
                    continue;
                }
                articles.add(ArticleMappingUtils.articleMapping(record,user));
            }

        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"获取文章列表失败");
        }
        return articles;
    }
}




