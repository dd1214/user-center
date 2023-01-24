package com.juejin.usercenter.mapper;

import com.juejin.usercenter.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
* @author zhaowei
* @description 针对表【article】的数据库操作Mapper
* @createDate 2023-01-21 14:13:27
* @Entity com.jidian.usercenter.model.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

}




