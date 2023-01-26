package com.juejin.usercenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juejin.usercenter.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juejin.usercenter.model.vo.ArticleVO;
import io.swagger.models.auth.In;


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
     * @param snapshoot 快照
     * @param preview  预览
     * @param category 分类
     * @param content  内容
     * @return 成功
     */

    Integer articleAdd(String title, String snapshoot, String preview, String category, String content , HttpServletRequest request);

    /**
     * 获取文章
     * @param id id
     * @return 文章
     */
    ArticleVO currentArticle(String id);

    /**
     *  分页查询
     * @param current 当前页数
     * @param size  每页条数
     * @param sortField 排序字段
     * @param sortOrder 排序类型
     * @param category 类别
     * @return 分页列表
     */

    ArrayList<ArticleVO> currentListArticle(long current, long size , String sortField, String sortOrder, String category);

    /**
     * 更新接口
     * @param content 更新内容
     * @return 成功
     */

    Boolean updateArticle(ArticleVO content);
}
