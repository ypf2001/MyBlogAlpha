package com.ypf.pojo;

import lombok.Data;

import java.util.List;

@Data
public class blogList {
    private String blog_id;
    private  String blog_name;
    private  String user_id;
    private  String summary;
    private  String create_time;
    private  String update_time;
    private List<articleList> articleCollections;
}
