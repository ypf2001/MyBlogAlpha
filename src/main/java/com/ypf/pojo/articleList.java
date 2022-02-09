package com.ypf.pojo;

import lombok.Data;

@Data
public class articleList {
    private  String article_id;
    private  String article_name;
    private  String article_text;
    private  String comment_id;
    private  String summary;
    private  String create_time;
    private  String update_time;
}
