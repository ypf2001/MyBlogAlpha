package com.ypf.dao;

import com.ypf.pojo.adminList;
import org.apache.ibatis.annotations.Param;

public interface adminListMapper {
    adminList findAdmin(@Param("admin_name") String admin_name);

    adminList login(@Param("admin_name") String admin_name, @Param("admin_pwd") String admin_pwd);
    boolean addAdmin(@Param("admin_name") String admin_name,@Param("admin_pwd") String admin_pwd,@Param("phone") String phone);
}
