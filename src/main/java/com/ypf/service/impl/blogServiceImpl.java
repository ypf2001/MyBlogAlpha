package com.ypf.service.impl;

import com.ypf.dao.blogArticleMapper;
import com.ypf.pojo.articleList;
import com.ypf.pojo.blogList;
import com.ypf.service.blogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class blogServiceImpl implements blogService {
@Resource
    blogArticleMapper blogArticleMapper;

    @Override
    public int saveBlog(String article_name,String article_quantity, String user_id) {

        int res =   blogArticleMapper.saveBlog(article_name,article_quantity,user_id);
        return res;
    }

    @Override
    public int updateBlog(String article_quantity, String blog_id) {
        int res =   blogArticleMapper.updateBlog(article_quantity,blog_id);
        return res;
    }

    @Override
    public int saveArticle(String article_name , String article_text, String comment_id, String summary) {

        int res = blogArticleMapper.saveArticle(article_name,article_text,comment_id,summary);
        return res;
    }

    @Override
    public int updateArticle(String article_text, String article_id) {
        int res = blogArticleMapper.updateArticle(article_text,article_id);
        return res;
    }
}
