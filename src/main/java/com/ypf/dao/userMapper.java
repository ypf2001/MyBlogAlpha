package com.ypf.dao;

import com.ypf.entity.articleEntity;
import com.ypf.entity.blogEntity;
import com.ypf.entity.userEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface userMapper {
    userEntity login (@Param("email") String email, @Param("password") String password);
    int register (@Param("user_name") String user_name,@Param("email") String email
            ,@Param("password") String password);
    int updateLogo(@Param("logo") String logo,@Param("user_id") String user_id);
    List<articleEntity> getUserBlogArticle(@Param("user_id") String user_id);
    userEntity getUserById(@Param("user_id")String user_id) ;
//    userEntity getUser(@Param("user_id") String user_id);
}
