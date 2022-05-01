package com.ypf.dao;

import com.ypf.entity.articleEntity;
import com.ypf.entity.blogEntity;
import com.ypf.entity.commentEntity;
import com.ypf.entity.userEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface blogArticleMapper {

     blogEntity getBlog(@Param("user_id") String user_id);
     int saveBlog(@Param("blog_name") String blog_name,
                  @Param("article_quantity")String article_quantity,
                  @Param("user_id") String user_id);
     int updateBlog(@Param("article_quantity")String article_quantity,
                    @Param("blog_id") String blog_id);
     int  saveArticle (@Param("article_name")String article_name,
                       @Param("article_text")String article_text,
                       @Param("summary")String summary,
                       @Param("user_id") String user_id,
                       @Param("blog_id") String blog_id);
     int updateArticle (@Param("article_text")String article_text,
                        @Param("article_id") String article_id);
     int createBlog(
                    @Param("blog_name") String blog_name,
                    @Param("article_quantity") String article_quantity,
                    @Param("user_id") String user_id,
                    @Param("summary")String summary);
     List<blogEntity> findArticleMsg (@Param("blog_id")String blog_id,@Param("pageIndex") int pageIndex);
     List<blogEntity> getArticle (@Param("blog_id") String blog_id);
     articleEntity getArticleById(@Param("article_id") String article_id);
     int addComment(@Param("comment_text")String comment_text,@Param("user_id")String user_id,@Param("article_id") String article_id,@Param("user_name") String user_name);
     List<commentEntity> getCommentByArticleId(@Param("article_id") String article_id);
     int subPraise(@Param("praise") String praise,@Param("comment_id") String comment_id);
     int getArticleNum(@Param("blog_id") String blog_id);
     int updateArticle(  @Param("article_text") String article_text,
                         @Param("article_name")  String article_name ,
                         @Param("summary") String summary,
                         @Param("article_id") String article_id);
}
