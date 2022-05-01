package com.ypf.entity;

import lombok.Data;

import java.util.List;

@Data
public class userEntity {
    private String user_id;
    private String user_name;
    private String real_name;
    private String email;
    private String password;
    private String gender;
    private String now_path;
    private String hobby;
    private String logo;
    private String sub_quantity;
    private String sub_user_id;
    private String create_time;
    private  String update_time;
    private  String school;
    private  blogEntity userBlog;
    private List<articleEntity> articleMsgList;
 }
