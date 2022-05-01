package com.ypf.service.impl;

import com.ypf.dao.blogArticleMapper;
import com.ypf.entity.articleEntity;
import com.ypf.entity.blogEntity;
import com.ypf.entity.commentEntity;
import com.ypf.service.blogService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class blogServiceImpl implements blogService {
    @Resource
    blogArticleMapper blogArticleMapper;

    @Override
    @CachePut("blog")
    public int saveBlog(String article_name, String article_quantity, String user_id) {

        return blogArticleMapper.saveBlog(article_name, article_quantity, user_id);

    }

    @Override
    @CachePut("blog")
    public int updateBlog(String article_quantity, String blog_id) {
        return blogArticleMapper.updateBlog(article_quantity, blog_id);

    }

    @Override
    @CachePut("article")
    public int saveArticle(String article_name, String article_text, String summary, String user_id, String blog_id) {

        return blogArticleMapper.saveArticle(article_name, article_text, summary, user_id, blog_id);

    }

    @Override
    @CachePut("article")
    public int updateArticle(String article_text, String article_id) {
        return blogArticleMapper.updateArticle(article_text, article_id);

    }

    @Override
    @Cacheable(cacheNames = "blog" )
    public blogEntity getBlog(String user_id) {
        return blogArticleMapper.getBlog(user_id);

    }

    @Override
    @CachePut(cacheNames = "blog")
    public int createBlog(String blog_name, String article_quantity, String user_id, String summary) {
        blogArticleMapper.createBlog(blog_name, article_quantity, user_id, summary);
        return 0;
    }

    @Override
    public List<blogEntity> findArticleMsg(String blog_id, int pageIndex) {
        return blogArticleMapper.findArticleMsg(blog_id, pageIndex);

    }

    @Override
    @Cacheable(cacheNames = "blog")
    public List<blogEntity> getArticle(String blog_id) {
        return blogArticleMapper.getArticle(blog_id);
    }

    @Override
    @Cacheable(cacheNames = "article")
    public articleEntity getArticleById(String article_id) {
        return blogArticleMapper.getArticleById(article_id);
    }

    @Override
    @CachePut(cacheNames = "comment")
    public int addComment(String comment_text, String user_id, String article_id, String user_name) {
        return blogArticleMapper.addComment(comment_text, user_id, article_id, user_name);
    }

    @Override
    @Cacheable("comment")
    public List<commentEntity> getCommentByArticleId(String article_id) {
        return blogArticleMapper.getCommentByArticleId(article_id);
    }

    @Override
    @CachePut("comment")
    public int subPraise(String praise, String comment_id) {
        return blogArticleMapper.subPraise(praise, comment_id);
    }

    @Override
    public int getArticleNum(String blog_id) {
        return blogArticleMapper.getArticleNum(blog_id);

    }

    @Override
    @CachePut("article")
    public int updateArticle(String article_text, String article_name, String summary, String article_id) {
        return blogArticleMapper.updateArticle(article_text,article_name,summary,article_id);
    }


}
