package com.ypf.service.impl;

import com.ypf.dao.userMapper;
import com.ypf.entity.articleEntity;
import com.ypf.entity.blogEntity;
import com.ypf.entity.userEntity;
import com.ypf.service.userService;
import com.ypf.utils.MD5Utils;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class userServiceImpl implements userService {
    @Resource
    userMapper userMapper;
    @Override
    @CachePut(cacheNames = "user" ,cacheManager = "cacheManager")
    public userEntity login(@Param("email") String email, @Param("password") String password) {
        String pwd = MD5Utils.MD5Encode(password,"utf-8");
        userEntity userEntity = userMapper.login(email,pwd);
        return userEntity;
    }

    @Override
    @CachePut("user")
    public int register(String user_name, String email, String password) {
    String pwd = MD5Utils.MD5Encode(password,"utf-8");

        return  userMapper.register(user_name,email,pwd);
    }

    @Override
    @CachePut("user")
    public int updateLogo(String logo, String user_id) {
       int res = userMapper.updateLogo(logo,user_id);
        return res;
    }

    @Override
    @Cacheable("article")
    public List<articleEntity> getUserBlogArticle(String user_id) {
        return userMapper.getUserBlogArticle(user_id);
    }

    @Override
    @Cacheable("user")
    public userEntity getUserById(String user_id) {
        return userMapper.getUserById(user_id);
    }
}
