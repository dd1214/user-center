package com.juejin.usercenter.controller;


import com.juejin.usercenter.common.BaseResponse;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.common.ResultUtils;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.model.dto.user.UserLoginRequest;
import com.juejin.usercenter.model.dto.user.UserRegisterRequest;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
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

    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String nickname = userRegisterRequest.getNickname();
        String userAvatar = userRegisterRequest.getUserAvatar();
        String userPassword = userRegisterRequest.getUserPassword();
        String introduction = userRegisterRequest.getIntroduction();
        if (StringUtils.isAnyBlank(nickname,userAvatar,userPassword,introduction)){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(userService.userRegister( nickname, userAvatar, userPassword, introduction));
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String nickname = userLoginRequest.getNickname();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(nickname,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(nickname, userPassword, request);
        return ResultUtils.success(user);
    }

    @GetMapping("/currentUser")
    public BaseResponse<User> currentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请先登录");
        }
        return ResultUtils.success(currentUser);
    }

}


