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
import com.juejin.usercenter.model.vo.CurrentListVO;
import com.juejin.usercenter.model.vo.ImportArticleVO;
import com.juejin.usercenter.service.ArticleService;
import com.juejin.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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
        String snapshot = articleAddRequest.getSnapshot();
        String preview = articleAddRequest.getPreview();
        String category = articleAddRequest.getCategory();
        String label = articleAddRequest.getLabel();
        String content = articleAddRequest.getContent();
        if (StringUtils.isAnyBlank(title,preview,content,category,label)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(articleService.articleAdd(title,snapshot,preview,category,content,label,request));
    }

    /**
     * 获取一条文章
     * @param id 文章id
     * @return 文章映射
     */

    @GetMapping("/current")
    public BaseResponse<ArticleVO> currentArticle(String id){
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
    public BaseResponse<CurrentListVO> currentListArticle(@RequestBody CurrentListArticle currentListArticle){
        if (currentListArticle == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //限制爬虫
        if(currentListArticle.getPageSize() > 50){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(articleService.currentListArticle(currentListArticle));
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
        ArrayList<ImportArticleVO> content = importArticleRequest.getContent();
        int flag = 0;
        for (ImportArticleVO articleVO : content) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("nickname",articleVO.getAuthor());
            List<User> users = userMapper.selectList(queryWrapper);
            if (users.size() < 1){
                User user = new User();
                user.setUserid(String.valueOf(System.currentTimeMillis()) +  (int)((Math.random()*9+1)*100000));
                user.setNickname(articleVO.getAuthor());
                user.setUseravatar(articleVO.getAvatar());
                userService.save(user);
            }
            QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
            String articleID = articleVO.getArticleID();
            articleQueryWrapper.eq("articleID",articleID);
            List<Article> articles = articleMapper.selectList(articleQueryWrapper);
            if (articles.size() == 0){
                Article article = new Article();
                BeanUtils.copyProperties(articleVO,article);
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

}
