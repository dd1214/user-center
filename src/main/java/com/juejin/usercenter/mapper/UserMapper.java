package com.juejin.usercenter.mapper;

import com.juejin.usercenter.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;

/**
* @author zhaowei
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-01-21 14:13:27
* @Entity com.jidian.usercenter.model.User
*/

@MapperScan
public interface UserMapper extends BaseMapper<User> {

}




