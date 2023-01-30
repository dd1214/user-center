package com.juejin.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juejin.usercenter.model.dto.article.CurrentListUserRequest;
import com.juejin.usercenter.model.dto.user.UpdateUserRequest;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.CurrentListUserVO;
import com.juejin.usercenter.model.vo.UserVO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
* @author zhaowei
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-01-21 14:13:27
*/
public interface UserService extends IService<User> {

    /**
     * @param nickname     昵称
     * @param userAccount   账号
     * @param avatar   头像
     * @param userPassword 密码
     * @param introduction 简介
     * @return 用户id
     */
    String userRegister(String userAccount, String nickname, String avatar, String userPassword, String introduction);

    /**
     * @param nickname     昵称
     * @param userPassword 密码
     * @param request      HTTP请求
     * @return 登录用户
     */
    User userLogin(String nickname, String userPassword, HttpServletRequest request);

    /**
     * 根据用户id查询
     *
     * @param id id
     * @return user
     */

    UserVO currentUserById(String id);


    /**
     * 获取用户列表
     * @param currentListUserRequest 请求
     * @return list
     */

    CurrentListUserVO currentUserList(CurrentListUserRequest currentListUserRequest);

    /**
     * 更新用户
     * @param updateUserRequest 请求
     * @return 成功
     */

    boolean updateUser(UpdateUserRequest updateUserRequest);

     /**
     * 用户注销
     *
     * @param request 请求
     * @return 成功
     */
    boolean userLogout(HttpServletRequest request);


}