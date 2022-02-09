package com.ypf.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypf.dao.adminListMapper;
import com.ypf.pojo.adminList;
import com.ypf.service.adminService;
import com.ypf.utils.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@Service
public class adminServiceImpl implements adminService {
    @Resource
    private adminListMapper adminListMapper;


    @Override
    public adminList login(String admin_name, String admin_pwd) {
        String md5Pwd = MD5Utils.MD5Encode(admin_name,"utf-8");
        return adminListMapper.login(admin_pwd,md5Pwd);
    }

    @Override
    public boolean addAdmin(String admin_name, String admin_pwd, String phone) {
        String md5Phone = MD5Utils.MD5Encode(phone,"utf-8");
        String md5Pwd = MD5Utils.MD5Encode(admin_name,"utf-8");
        return adminListMapper.addAdmin(admin_pwd,md5Pwd,md5Phone);
    }

    @Override
    public adminList findAdmin(String admin_name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
       adminList admin  =adminListMapper.findAdmin(admin_name);
       return admin;
    }

}
