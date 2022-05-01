package com.ypf.pojo;

import lombok.Data;

import java.util.List;

@Data
public class articleList {
    private  String article_id;
    private  String article_name;
    private  String article_text;
//    补充说明
    private  String summary;
    private String comment_id;
    private String user_id;
    private  String blog_id;
    private  String create_time;
    private  String update_time;

}
