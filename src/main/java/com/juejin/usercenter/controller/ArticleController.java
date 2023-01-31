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
import com.juejin.usercenter.model.entity.Home;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.article.ArticleVO;
import com.juejin.usercenter.model.vo.article.CurrentListArticleVO;
import com.juejin.usercenter.model.vo.article.ImportArticleVO;
import com.juejin.usercenter.service.ArticleService;
import com.juejin.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.storage.Configuration;
import com.qiniu.http.Response;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.juejin.usercenter.MyApplicationRunner.HOME_CONFIG;
import static com.juejin.usercenter.constant.ArticleConstant.ACCESS_KEY;
import static com.juejin.usercenter.constant.ArticleConstant.SECRET_KEY;

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

    @PostMapping("/current_list")
    public BaseResponse<CurrentListArticleVO> currentListArticle(@RequestBody CurrentListArticleRequest currentListArticle){
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
                user.setViewCount(articleVO.getViewCount());
                user.setCollectCount(articleVO.getCollectCount());
                user.setAvatar(articleVO.getAvatar());
                userService.save(user);
            }else {
                User user = users.get(0);
                Integer collectCount = user.getCollectCount();
                Integer viewCount = user.getViewCount();
                user.setCollectCount(collectCount + articleVO.getCollectCount());
                user.setViewCount(viewCount + articleVO.getViewCount());
                userService.update(user,queryWrapper);
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

    @PostMapping("/update")
    public BaseResponse<Boolean> updateArticle(@RequestBody UpdateArticleRequest updateArticleRequest){
        if (updateArticleRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String id = updateArticleRequest.getId();
        if (id == null || id.length() != 19){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(articleService.updateArticle(updateArticleRequest));
    }

    /**
     * 删除文章
     * @param deleteArticleRequest 请求
     * @return 成功
     */

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteArticle(@RequestBody DeleteArticleRequest deleteArticleRequest){
        if (deleteArticleRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        String id = deleteArticleRequest.getId();
        if (id == null || id.length() != 19){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        queryWrapper.eq("articleID",id);
        return ResultUtils.success(articleService.remove(queryWrapper));
    }

    /**
     * 批量更新文章状态
     * @param updateStatusRequest 请求
     * @return 成功条数
     */

    @PostMapping("/updateStatus")
    public BaseResponse<Integer> updateStatus(@RequestBody UpdateStatusRequest updateStatusRequest){
        if (updateStatusRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (updateStatusRequest.getContent().size() <= 0 || updateStatusRequest.getContent() == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (updateStatusRequest.getContent().size() > 100){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"最大支持同时修改100条！");
        }
        return ResultUtils.success(articleService.updateStatus(updateStatusRequest.getContent()));
    }

    /**
     *  上传图片
     * @param image 图片
     * @return url
     * @throws IOException 异常
     */

    @PostMapping("/uploadImg")
    public BaseResponse<String> uploadArticleImg(@RequestParam MultipartFile image) throws IOException {
        if (image.isEmpty()) {
           throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        byte[] bytes = image.getBytes();
        String imageName = UUID.randomUUID().toString();
        String bucketName = "juejincms";
        Configuration cfg = new Configuration();
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(bucketName);
        Response r = uploadManager.put(bytes, imageName, token);
        String url = "http://rpaka2fkv.hb-bkt.clouddn.com/" + imageName;
        if (!r.isOK()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"文件上传失败");
        }
        return ResultUtils.success(url);
    }

    /**
     * 获取全局配置
     * @return 配置类（json）
     */

    @GetMapping("/getHomeConfig")
    public BaseResponse<Home> getHomeConfig(){
        if (HOME_CONFIG == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(HOME_CONFIG);
    }

    @PostMapping("/setHomeConfig")
    public BaseResponse<Boolean> setHomeConfig(@RequestBody SetHomeConfigRequest setHomeConfigRequest){
        if (setHomeConfigRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Home homeConfig = setHomeConfigRequest.getHomeConfig();
        if (homeConfig == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(articleService.setHomeConfig(homeConfig));
    }



}
