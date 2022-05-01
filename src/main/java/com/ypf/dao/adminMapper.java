package com.ypf.dao;

import com.ypf.entity.adminEntity;
import org.apache.ibatis.annotations.Param;

public interface adminMapper {
    adminEntity findAdmin(@Param("admin_name") String admin_name);

    adminEntity login(@Param("admin_name") String admin_name, @Param("admin_pwd") String admin_pwd);
    boolean addAdmin(@Param("admin_name") String admin_name,@Param("admin_pwd") String admin_pwd,@Param("phone") String phone);
}
