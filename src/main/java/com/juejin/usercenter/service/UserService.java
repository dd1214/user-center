package com.juejin.usercenter.service;

import com.juejin.usercenter.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author zhaowei
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-01-21 14:13:27
*/
public interface UserService extends IService<User> {

    /**
     * @param nickname     昵称
     * @param userAvatar   头像
     * @param userPassword 密码
     * @param introduction 简介
     * @return 用户id
     */
    String userRegister(String nickname, String userAvatar, String userPassword, String introduction);

    /**
     * @param nickname     昵称
     * @param userPassword 密码
     * @param request      HTTP请求
     * @return 登录用户
     */
    User userLogin(String nickname, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request 请求
     * @return 成功
     */
    boolean userLogout(HttpServletRequest request);


}