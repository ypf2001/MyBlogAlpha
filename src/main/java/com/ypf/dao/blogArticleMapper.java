package com.ypf.dao;

import com.ypf.pojo.articleList;
import com.ypf.pojo.blogList;
import org.apache.ibatis.annotations.Param;



public interface blogArticleMapper {

     int saveBlog(@Param("blog_name") String blog_name, @Param("article_quantity")String article_quantity, @Param("user_id") String user_id);
     int updateBlog(@Param("article_quantity")String article_quantity,@Param("blog_id") String blog_id);
     int  saveArticle (@Param("article_name")String article_name,@Param("article_text")String article_text,@Param("comment_id")String comment_id,@Param("summary")String summary);
     int updateArticle (@Param("article_text")String article_text,@Param("article_id") String article_id);

}
