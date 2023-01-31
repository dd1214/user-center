package com.juejin.usercenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.juejin.usercenter.common.ErrorCode;
import com.juejin.usercenter.exception.BusinessException;
import com.juejin.usercenter.mapper.UserMapper;
import com.juejin.usercenter.model.entity.Home;
import com.juejin.usercenter.model.entity.User;
import com.juejin.usercenter.model.vo.home.HomeUserListVO;
import com.juejin.usercenter.model.vo.home.UserListVO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Component
@Order(1)
public class MyApplicationRunner implements ApplicationRunner {

    @javax.annotation.Resource
    private UserMapper userMapper;

    public static Home HOME_CONFIG;

    @Value("classpath:/config/config.json")
    private Resource config;
    public JSONObject config(){
        JSONObject json;
        try {
            String areaData =  IOUtils.toString(config.getInputStream(), StandardCharsets.UTF_8);
            json =  (JSONObject) JSON.parse(areaData);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return json;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            Home home = JSONObject.toJavaObject(config(), Home.class);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("collectCount");
            queryWrapper.last("limit 3");
            List<User> users = userMapper.selectList(queryWrapper);
            ArrayList<UserListVO> userListVOS = new ArrayList<>();
            for (User user : users) {
                UserListVO userListVO = new UserListVO();
                BeanUtils.copyProperties(user,userListVO);
                userListVOS.add(userListVO);
            }
            ArrayList<HomeUserListVO> userList = home.getUserList();
            String title = userList.get(0).getTitle();
            HomeUserListVO homeUserListVO = new HomeUserListVO();
            homeUserListVO.setTitle(title);
            homeUserListVO.setList(userListVOS);
            userList.set(0,homeUserListVO);
            home.setUserList(userList);
            HOME_CONFIG = home;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}