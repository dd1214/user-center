package com.juejin.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.service.UserService;
import com.juejin.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.juejin.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author zhaowei
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-01-21 14:13:27
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "juejin";

    @Override
    public String userRegister(String nickname, String userAvatar, String userPassword, String introduction) {
        if (nickname.length() > 20 || nickname.length() < 2){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"昵称长度不合法");
        }
        if (userPassword.length() <= 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度至少8位");
        }
        if (introduction.length() > 100){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"简介过长");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname",nickname);
        Long selectUser = userMapper.selectCount(queryWrapper);
        if (selectUser > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"昵称重复");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        String userId = String.valueOf(System.currentTimeMillis()) +  (int)((Math.random()*9+1)*100000);
        User user = new User();
        user.setUserid(userId);
        user.setNickname(nickname);
        user.setUseravatar(userAvatar);
        user.setUserpassword(encryptPassword);
        user.setIntroduction(introduction);
        boolean save = this.save(user);
        if (!save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户创建失败");
        }
        return userId;
    }

    @Override
    public User userLogin(String nickname, String userPassword, HttpServletRequest request) {
        if (nickname.length() > 20 || nickname.length() < 2){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"昵称长度不合法");
        }
        if (userPassword.length() <= 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度至少8位");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname",nickname);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        if (!encryptPassword.equals(user.getUserpassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user;
    }
}




