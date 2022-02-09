package com.ypf.dao;

import com.ypf.pojo.userList;
import org.apache.ibatis.annotations.Param;

public interface userMapper {
    userList login (@Param("email") String email,@Param("password") String password);
    int register (@Param("user_name") String user_name,@Param("email") String email,@Param("password") String password);
}
