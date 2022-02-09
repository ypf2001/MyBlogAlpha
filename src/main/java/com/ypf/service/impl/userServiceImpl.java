package com.ypf.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypf.dao.userMapper;
import com.ypf.pojo.userList;
import com.ypf.service.userService;
import com.ypf.utils.MD5Utils;
import com.ypf.utils.jwtToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class userServiceImpl implements userService {
    @Resource
    userMapper userMapper;
    @Override
    public userList login(@Param("email") String email, @Param("password") String password) {
        String pwd = MD5Utils.MD5Encode(password,"utf-8");
        userList userList = userMapper.login(email,pwd);
        return userList;
    }

    @Override
    public int register(String user_name, String email, String password) {
    String pwd = MD5Utils.MD5Encode(password,"utf-8");

        return  userMapper.register(user_name,email,pwd);
    }
}
