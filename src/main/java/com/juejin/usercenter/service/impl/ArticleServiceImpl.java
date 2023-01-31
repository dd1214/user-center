package com.juejin.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.constant.CommonConstant;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.mapper.ArticleMapper;
import com.juejin.usercenter.mapper.UserMapper;
import com.juejin.usercenter.model.dto.article.CurrentListArticleRequest;
import com.juejin.usercenter.model.dto.article.UpdateArticleRequest;
import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.article.ArticleVO;
import com.juejin.usercenter.model.vo.article.AuditArticleVO;
import com.juejin.usercenter.model.vo.article.CurrentListArticleVO;
import com.juejin.usercenter.model.vo.article.UpdateArticleVO;
import com.juejin.usercenter.service.ArticleService;
import com.juejin.usercenter.utils.ArticleMappingUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;

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
     * @param snapshot 快照
     * @param preview  预览
     * @param category 分类
     * @param content  内容
     * @param request 请求
     * @return 成功
     */

    @Override
    public Integer articleAdd(String title, String snapshot, String preview, String category, String content,String label, HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请先登录");
        }
        if (StringUtils.isAnyBlank(label)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标签不能为空");
        }
        if (title.length() > 100 || title.length() < 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"标题长度不符合要求");
        }
        if (preview.length() != 100){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"预览长度不符合要求");
        }
        if (StringUtils.isAnyBlank(category)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"分类不能为空");
        }
        String articleId = String.valueOf(System.currentTimeMillis()) +  (int)((Math.random()*9+1)*100000);
        Article article = new Article();
        article.setArticleID(articleId);
        article.setAuthor(currentUser.getUserid());
        article.setTitle(title);
        article.setSnapshot(snapshot);
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
        queryWrapper.eq("nickname",article.getAuthor());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"作者信息获取失败");
        }
        return ArticleMappingUtils.articleMapping(article,user);
    }

    /**
     * 按条件分页查询
     * @param currentListArticle 请求参数
     * @return 文章列表
     */

    @Override
    public CurrentListArticleVO currentListArticle(CurrentListArticleRequest currentListArticle) {
        if (currentListArticle == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArrayList<ArticleVO> articles = new ArrayList<>();
        long current = currentListArticle.getCurrent();
        long size = currentListArticle.getPageSize();
        String sortField = currentListArticle.getSortField();
        String sortOrder = currentListArticle.getSortOrder();
        String content = currentListArticle.getContent();
        Integer articleStatus = currentListArticle.getArticleStatus();
        String label = currentListArticle.getLabel();
        String category = currentListArticle.getCategory();
        long total = 0;
        try {
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.eq(articleStatus != -1,"articleStatus",articleStatus);
            articleQueryWrapper.like(StringUtils.isNoneBlank(label),"label",label);
            articleQueryWrapper.eq(StringUtils.isNoneBlank(category),"category",category);
            articleQueryWrapper.like(StringUtils.isNotBlank(content),"content",content).or()
                    .like(StringUtils.isNotBlank(content),"title",content).or()
                            .like(StringUtils.isNotBlank(content),"preview",content);
            articleQueryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                    sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortField);
            Page<Article> page = articleMapper.selectPage(new Page<>(current, size), articleQueryWrapper);
            total = page.getTotal();
            for (Article record : page.getRecords()) {
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("nickname",record.getAuthor());
                User user = userMapper.selectOne(queryWrapper);
                if (user == null){
                    continue;
                }
                articles.add(ArticleMappingUtils.articleMapping(record,user));
            }
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"获取文章列表失败");
        }
        Collections.shuffle(articles);
        CurrentListArticleVO currentListVO = new CurrentListArticleVO();
        currentListVO.setList(articles);
        currentListVO.setTotal(total);
        return currentListVO;
    }

    /**
     * 更新
     * @param data 更新内容
     * @return 成功
     */

    @Override
    public Boolean updateArticle(UpdateArticleRequest data) {
        String id = data.getId();
        UpdateArticleVO cont = data.getContent();
        String content = cont.getContent();
        String category = cont.getCategory();
        String preview = cont.getPreview();
        String title = cont.getTitle();
        String snapshot = cont.getSnapshot();
        if (StringUtils.isAnyBlank(content,category,preview,title,snapshot)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Article article = new Article();
        article.setSnapshot(snapshot);
        article.setTitle(title);
        article.setPreview(preview);
        article.setContent(content);
        article.setCategory(category);
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("articleID",id);
        int update = articleMapper.update(article, articleQueryWrapper);
        if (update < 1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"更新失败");
        }
        return true;
    }

    /**
     * 批量修改文章状态
     * @param content 内容
     * @return 成功条数
     */

    @Override
    public Integer updateStatus(ArrayList<AuditArticleVO> content) {
        int flag = 0;
        for (AuditArticleVO articleVO : content) {
            String id = articleVO.getId();
            if (StringUtils.isAnyBlank(id) || id.length() != 19){
                continue;
            }
            Article article = new Article();
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            articleQueryWrapper.eq("articleID",id);
            Article selectOne = articleMapper.selectOne(articleQueryWrapper);
            BeanUtils.copyProperties(selectOne,article);
            article.setArticleStatus(2);
            flag += articleMapper.update(article, articleQueryWrapper);
        }
        return flag;
    }
}




