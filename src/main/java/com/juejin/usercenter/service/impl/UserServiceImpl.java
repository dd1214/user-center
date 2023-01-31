package com.juejin.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.constant.CommonConstant;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.mapper.UserMapper;
import com.juejin.usercenter.model.dto.article.CurrentListUserRequest;
import com.juejin.usercenter.model.dto.user.UpdateUserRequest;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.user.CurrentListUserVO;
import com.juejin.usercenter.model.vo.user.UpdateUserVO;
import com.juejin.usercenter.model.vo.user.UserVO;
import com.juejin.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collections;

import static com.juejin.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "juejin";

    /**
     * 注册
     * @param nickname     昵称
     * @param userAvatar   头像
     * @param userPassword 密码
     * @param introduction 简介
     * @return 用户id
     */

    @Override
    public String userRegister(String userAccount, String nickname, String userAvatar, String userPassword, String introduction) {
        if (userAccount.length() != 11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度不合法");
        }
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
        queryWrapper.eq("userAccount",userAccount);
        Long selectUser = userMapper.selectCount(queryWrapper);
        if (selectUser > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        String userId = String.valueOf(System.currentTimeMillis()) +  (int)((Math.random()*9+1)*100000);
        User user = new User();
        user.setUserid(userId);
        user.setUserAccount(userAccount);
        user.setNickname(nickname);
        user.setAvatar(userAvatar);
        user.setUserPassword(encryptPassword);
        user.setIntroduction(introduction);
        boolean save = this.save(user);
        if (!save){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户创建失败");
        }
        return userId;
    }

    /**
     * 登录
     * @param userAccount  账号
     * @param userPassword 密码
     * @param request      HTTP请求
     * @return 用户封装类
     */

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (userAccount.length() != 11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度不合法");
        }
        if (userPassword.length() <= 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度至少8位");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        if (!encryptPassword.equals(user.getUserPassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        return user;
    }

    /**
     *
     * @param id id
     * @return 用户封装类
     */

    @Override
    public UserVO currentUserById(String id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",id);
        User user = userMapper.selectOne(queryWrapper);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public CurrentListUserVO currentUserList(CurrentListUserRequest currentListUserRequest) {
        ArrayList<UserVO> users = new ArrayList<>();
        long current = currentListUserRequest.getCurrent();
        long size = currentListUserRequest.getPageSize();
        String sortField = currentListUserRequest.getSortField();
        String sortOrder = currentListUserRequest.getSortOrder();
        String content = currentListUserRequest.getContent();
        String userAccount = currentListUserRequest.getUserAccount();
        long total = 0;
        try {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userRole",0);
            userQueryWrapper.eq(StringUtils.isNoneBlank(userAccount),"userAccount",userAccount);
            userQueryWrapper.like(StringUtils.isNotBlank(content),"nickname",content).or()
                    .like(StringUtils.isNotBlank(content),"introduction",content).or();
            userQueryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                    sortOrder.equals(CommonConstant.SORT_ORDER_ASC),sortField);
            Page<User> page = userMapper.selectPage(new Page<>(current, size), userQueryWrapper);
            for (int i = 0; i < page.getRecords().size(); i++) {
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(page.getRecords().get(i),userVO);
                users.add(userVO);
            }
            total = page.getTotal();
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"获取用户列表失败");
        }
        Collections.shuffle(users);
        CurrentListUserVO currentListUserVO = new CurrentListUserVO();
        currentListUserVO.setList(users);
        currentListUserVO.setTotal(total);
        return currentListUserVO;
    }

    @Override
    public boolean updateUser(UpdateUserRequest updateUserRequest) {
        String id = updateUserRequest.getId();
        UpdateUserVO content = updateUserRequest.getContent();
        String avatar = content.getAvatar();
        String introduction = content.getIntroduction();
        String nickname = content.getNickname();
        if (StringUtils.isAnyBlank(avatar,id,introduction,nickname)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User user = new User();
        user.setAvatar(avatar);
        user.setNickname(nickname);
        user.setIntroduction(introduction);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",id);
        int update = userMapper.update(user, queryWrapper);
        if (update < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户更新失败");
        }
        return true;
    }

    /**
     * 用户注销
     *
     * @param request 请求
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

}




