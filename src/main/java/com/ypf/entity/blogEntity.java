package com.ypf.entity;

import lombok.Data;

import java.util.List;

@Data
public class blogEntity {
    private String blog_id;
    private  String blog_name;
    private  String user_id;
    private  String summary;
    private  String create_time;
    private  String update_time;
    private List<articleEntity> articleCollections;
    private List<articleEntity> articleList;
}
