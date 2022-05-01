package com.ypf.service.impl;


import com.ypf.dao.adminMapper;
import com.ypf.entity.adminEntity;
import com.ypf.service.adminService;
import com.ypf.utils.MD5Utils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@Service
public class adminServiceImpl implements adminService {
    @Resource
    private adminMapper adminMapper;


    @Override
    @Cacheable("admin")
    public adminEntity login(String admin_name, String admin_pwd) {
        String md5Pwd = MD5Utils.MD5Encode(admin_pwd,"utf-8");
        return adminMapper.login(admin_name,md5Pwd);
    }

    @Override
    @CachePut("admin")
    public boolean addAdmin(String admin_name, String admin_pwd, String phone) {
        String md5Phone = MD5Utils.MD5Encode(phone,"utf-8");
        String md5Pwd = MD5Utils.MD5Encode(admin_name,"utf-8");
        return adminMapper.addAdmin(admin_pwd,md5Pwd,md5Phone);
    }

    @Override
    @Cacheable("admin")
    public adminEntity findAdmin(String admin_name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
       adminEntity admin  = adminMapper.findAdmin(admin_name);
       return admin;
    }

}
