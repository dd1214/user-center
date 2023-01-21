package com.juejin.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.service.UserService;
import com.juejin.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zhaowei
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-01-21 14:13:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




