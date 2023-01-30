package com.juejin.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juejin.usercenter.model.dto.article.CurrentListArticle;
import com.juejin.usercenter.model.entity.Article;
import com.juejin.usercenter.model.vo.ArticleVO;
import com.juejin.usercenter.model.vo.CurrentListVO;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
* @author zhaowei
* @description 针对表【article】的数据库操作Service
* @createDate 2023-01-21 14:13:27
*/
public interface ArticleService extends IService<Article> {

    /**
     *  添加文章
     * @param title 标题
     * @param snapshot 快照
     * @param preview  预览
     * @param category 分类
     * @param content  内容
     * @param label  标签
     * @return 成功
     */

    Integer articleAdd(String title, String snapshot, String preview, String category, String content , String label, HttpServletRequest request);

    /**
     * 获取文章
     * @param id id
     * @return 文章
     */
    ArticleVO currentArticle(String id);

    /**
     *  分页查询
     * @param currentListArticle 封装请求类
     * @return 分页列表
     */

    CurrentListVO currentListArticle(CurrentListArticle currentListArticle);

    /**
     * 更新接口
     * @param content 更新内容
     * @return 成功
     */

    Boolean updateArticle(ArticleVO content);
}
