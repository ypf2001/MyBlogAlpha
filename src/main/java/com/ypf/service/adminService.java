package com.ypf.service;

import com.ypf.entity.adminEntity;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;

public interface adminService {
    adminEntity login(@Param("admin_name")String admin_name,@Param("admin_pwd") String admin_pwd);
    boolean addAdmin(@Param("admin_name") String admin_name, @Param("admin_pwd") String admin_pwd, @Param("phone") String phone);
    adminEntity findAdmin(@Param("admin_name") String admin_name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

}
