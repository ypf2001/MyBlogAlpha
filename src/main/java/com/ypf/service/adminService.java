package com.ypf.service;

import com.ypf.pojo.adminList;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;

public interface adminService {
    adminList login(String admin_name,String admin_pwd);
    boolean addAdmin(@Param("admin_name") String admin_name, @Param("admin_pwd") String admin_pwd, @Param("phone") String phone);
    adminList findAdmin(@Param("admin_name") String admin_name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

}
