package com.juejin.usercenter.service;

import com.juejin.usercenter.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import javax.servlet.http.HttpServletRequest;

/**
* @author zhaowei
* @description 针对表【article】的数据库操作Service
* @createDate 2023-01-21 14:13:27
*/
public interface ArticleService extends IService<Article> {

    /**
     *  添加文章
     * @param title 标题
     * @param snapshoot 快照
     * @param preview  预览
     * @param category 分类
     * @param content  内容
     * @return 成功
     */

    Integer articleAdd(String title, String snapshoot, String preview, String category, String content , HttpServletRequest request);
}
