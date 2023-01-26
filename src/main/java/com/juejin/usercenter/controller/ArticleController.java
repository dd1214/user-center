package com.juejin.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.juejin.usercenter.common.BaseResponse;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.common.ResultUtils;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.mapper.ArticleMapper;
import com.juejin.usercenter.mapper.UserMapper;
import com.juejin.usercenter.model.dto.article.*;
import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.ArticleVO;
import com.juejin.usercenter.service.ArticleService;
import com.juejin.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.sun.javafx.font.FontResource.SALT;

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

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

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

    /**
     * 分页获取列表
     * @param currentListArticle 请求
     * @return 列表
     */

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

    /**
     * 批量导入
     * @param importArticleRequest 请求
     * @return 成功条数
     */

    @PostMapping("/import")
    public BaseResponse<Integer> importArticle(@RequestBody ImportArticleRequest importArticleRequest){
        if (importArticleRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        ArrayList<ArticleVO> content = importArticleRequest.getContent();
        int flag = 0;
        for (ArticleVO articleVO : content) {
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            String article_id = articleVO.getArticle_id();
            articleQueryWrapper.eq("articleID",article_id);
            List<Article> articles = articleMapper.selectList(articleQueryWrapper);
            if (articles.size() == 0){
                Article article = articleTo(articleVO);
                boolean save;
                try {
                    save = articleService.save(article);
                } catch (Exception e) {
                    continue;
                }
                if (save){
                    flag++;
                }
            }
        }
        return ResultUtils.success(flag);
    }

    /**
     * 文章更新
     * @param updateArticleRequest 请求
     * @return 成功
     */

    @PostMapping("update")
    public BaseResponse<Boolean> updateArticle(@RequestBody UpdateArticleRequest updateArticleRequest){
        if (updateArticleRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ArticleVO content = updateArticleRequest.getContent();
        return ResultUtils.success(articleService.updateArticle(content));
    }

    /**
     * 删除文章
     * @param deleteArticleRequest 请求
     * @return 成功
     */

    @PostMapping("delete")
    public BaseResponse<Boolean> deleteArticle(@RequestBody DeleteArticleRequest deleteArticleRequest){
        if (deleteArticleRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.removeById(deleteArticleRequest.getId()));
    }

    //TODO 图片上传、批量/单个文章审核、

    /**
     * articleVO类型转换
     * @param articleVO 封装类
     * @return article
     */
    public Article articleTo(ArticleVO articleVO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname",articleVO.getAuthor());
        queryWrapper.eq("userAvatar",articleVO.getAvatar());
        String userID = "";
        List<User> users = userMapper.selectList(queryWrapper);
        User userTo = null;
        if (users.size() == 1){
            userTo = users.get(0);
        }
        if (userTo == null){
            User user = new User();
            user.setUserid(String.valueOf(System.currentTimeMillis()) +  (int)((Math.random()*9+1)*100000));
            user.setNickname(articleVO.getAuthor());
            user.setUseravatar(articleVO.getAvatar());
            user.setLikesnumber(articleVO.getCollect_count());
            user.setReadingquantity(articleVO.getView_count());
            user.setUserpassword(DigestUtils.md5DigestAsHex((SALT + "123456789").getBytes()));
            userService.save(user);
            userID = user.getUserid();
        }else {
            userTo.setReadingquantity(userTo.getReadingquantity() + articleVO.getView_count());
            userTo.setLikesnumber(userTo.getLikesnumber() + articleVO.getCollect_count());
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userID",userTo.getUserid());
            userMapper.update(userTo,userQueryWrapper);
            userID = userTo.getUserid();
        }
        Article article = new Article();
        article.setArticleid(articleVO.getArticle_id());
        article.setCreateuser(userID);
        article.setSnapshoot(articleVO.getSnapshot());
        article.setTitle(articleVO.getTitle());
        article.setPreview(articleVO.getPreview());
        article.setContent(articleVO.getContent());
        article.setCategory(articleVO.getCategory());
        article.setArticlestatus(1);
        return article;
    }
}
