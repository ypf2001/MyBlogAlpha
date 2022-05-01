package com.ypf.entity;

import lombok.Data;

import java.util.List;

@Data
public class commentEntity {
    private  String comment_id;
    private  String comment_text;
    private  String user_id;
    private  String article_id;
    private String create_time;
    private  String update_time;
    private  String praise;
    private  String reduce;
    private List<userEntity> userList;
}
