package com.juejin.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.juejin.usercenter.common.BaseResponse;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.common.ResultUtils;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.model.dto.article.CurrentListUserRequest;
import com.juejin.usercenter.model.dto.user.UpdateUserRequest;
import com.juejin.usercenter.model.dto.user.UserLoginRequest;
import com.juejin.usercenter.model.dto.user.UserRegisterRequest;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.user.CurrentListUserVO;
import com.juejin.usercenter.model.vo.user.UserVO;
import com.juejin.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.juejin.usercenter.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户接口
 *
 * @author 梁钊炜
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     * @param userRegisterRequest 请求
     * @return id
     */

    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String nickname = userRegisterRequest.getNickname();
        String userAvatar = userRegisterRequest.getUserAvatar();
        String userPassword = userRegisterRequest.getUserPassword();
        String introduction = userRegisterRequest.getIntroduction();
        if (StringUtils.isAnyBlank(nickname,userAvatar,userPassword,introduction,userAccount)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(userService.userRegister( userAccount,nickname, userAvatar, userPassword, introduction));
    }

    /**
     * 登录
     * @param userLoginRequest 请求
     * @param request 返回
     * @return user
     */

    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return ResultUtils.success(userVO);
    }

    /**
     * 获取当前登录用户
     * @param request 请求
     * @return user
     */

    @GetMapping("/currentUser")
    public BaseResponse<UserVO> currentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请先登录");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(currentUser,userVO);
        return ResultUtils.success(userVO);
    }

    /**
     * 根据id获取用户
     * @param id id
     * @return user
     */

    @GetMapping("/currentUserById")
    public BaseResponse<UserVO> currentUserById(String id){
        if (id == null || id.length() != 19){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(userService.currentUserById(id));
    }

    /**
     * 获取用户列表
     * @param currentListUserRequest 请求
     * @return list
     */

    @PostMapping("/currentUserList")
    public BaseResponse<CurrentListUserVO> currentUserList(@RequestBody CurrentListUserRequest currentListUserRequest){
        if (currentListUserRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        //限制爬虫
        if(currentListUserRequest.getPageSize() > 50){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(userService.currentUserList(currentListUserRequest));
    }

    /**
     * 更新接口
     * @param updateUserRequest 请求
     * @return 成功
     */

    @PostMapping("/updateUser")
    public BaseResponse<Boolean> updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        if (updateUserRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(userService.updateUser(updateUserRequest));
    }

    /**
     * 删除用户
     * @param id id
     * @return 成功
     */

    @GetMapping("/delete")
    public BaseResponse<Boolean> deleteUser(String id){
        if (id == null || id.length() != 19){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"id格式不符合要求");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID",id);
        boolean remove = userService.remove(queryWrapper);
        return ResultUtils.success(remove);
    }

    /**
     * 用户注销
     * @param request 请求
     * @return 成功
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

}


