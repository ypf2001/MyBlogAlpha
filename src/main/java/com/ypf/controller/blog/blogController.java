package com.ypf.controller.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypf.entity.articleEntity;
import com.ypf.entity.blogEntity;
import com.ypf.entity.userEntity;
import com.ypf.service.blogService;
import com.ypf.service.impl.redisUtils;
import com.ypf.utils.deflaterUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class blogController {
    @Resource
    redisUtils redisUtils;
    @Resource
    ObjectMapper objectMapper;
    @Resource
    blogService blogService;
    @Resource
    Logger logger;

    //    produce 指定返回类型为json
    @GetMapping("/blog/write")
    public String write() {
        return "blog/write";
    }

    @PostMapping("/blog/write")
    public String update(@RequestParam("article_id") String article_id, @RequestParam("content") String content, @RequestParam("title") String title, Model model) {
        model.addAttribute("article_id", article_id);
        model.addAttribute("content", content);
        model.addAttribute("title", title);
        logger.info("修改表单已经提交");
        return "blog/write";
    }

    @PostMapping("/updateArticle")
    @ResponseBody
    public String updateArticle(
            @RequestParam("article_text") String article_text,
            @RequestParam("article_name") String article_name,
            @RequestParam("summary") String summary,
            @RequestParam("article_id") String article_id, Model model) {
        Map<Object, Object> map = new HashMap<>();
        int i = blogService.updateArticle(article_text, article_name, summary, article_id);
        if (i > 0) {
            map.put("msg", 1);
            model.addAttribute("article_id", null);
            model.addAttribute("content", null);
            model.addAttribute("title", null);
        } else {
            map.put("msg", 0);
        }
        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/blog/logout")
    public String logout(HttpSession session, HttpServletResponse response) {

        session.removeAttribute("user");
        session.removeAttribute("blog");
        logger.info("session已经注销");
        return "blog/logout";
    }

    @PostMapping("/blogIndex")
    public String validateUser(
            @RequestParam("email") String email,
            @RequestParam("password") String Password,
            HttpSession session, Model model   ) throws IOException {
        return "blog/blogIndex";
    }

    @GetMapping(value = "/blog/showArticle/{article_id}")
    public String showArticle(@PathVariable String article_id, Model model, HttpSession session) throws Exception {
        articleEntity article = blogService.getArticleById(article_id);
        model.addAttribute("mainHtml", deflaterUtils.unzipString(article.getArticle_text()));
        model.addAttribute("title", article.getArticle_name());


//        判断是否登录
        if (session.getAttribute("blog") != null) {
            blogEntity blog = ((blogEntity) session.getAttribute("blog"));
            String collect_key = "MyBlog:" + blog.getBlog_id() + ":" + ":" + article_id + ":" + "isCollected";
            if (redisUtils.get(collect_key) == null || redisUtils.get(collect_key).equals("0")) {
                model.addAttribute("isCollected", "0");
            } else {
                model.addAttribute("isCollected", "1");
            }
        }
        model.addAttribute("comment_list", blogService.getCommentByArticleId(article_id));
        return "blog/showArticle";
    }

    @PostMapping("/blog/collectHandle")
    @ResponseBody
    public String collectHandle(HttpSession session, @RequestParam("article_id") String article_id, @RequestParam("isCollected") String isCollected) {
        blogEntity blog = ((blogEntity) session.getAttribute("blog"));
        String collect_key = "MyBlog:" + blog.getBlog_id() + ":" + ":" + article_id + ":" + "isCollected";

        if (isCollected.equals("0")) {
            isCollected = "1";
        } else {
            isCollected = "0";
        }
        if (redisUtils.set(collect_key, isCollected)) {
            return "{\"msg\":1}";
        } else {
            return "{\"msg\":0}";
        }
    }

    @PostMapping({"/dataSubmit"})
    @ResponseBody
    public String upload_article(@RequestParam("article_name") String article_name,
                                 @RequestParam("article_text") String article_text,
                                 @RequestParam("summary") String summary,
                                 HttpServletRequest request) throws JsonProcessingException {
        Map<Object, Object> map = new HashMap<>();
        if (summary == null) {
            summary = "写点什么吧!";
            map.put("msg", "0");
        } else if (article_name == null || article_name.equals("")) {
            System.out.println("前端过滤失败，标题不能为空");
            map.put("msg", "0");
        } else if (article_name.length() > 200) {
            System.out.println("前端过滤失败，标题过长");
            map.put("msg", "0");
        } else if (article_text.length() < 50) {
            System.out.println("前端过滤失败，内容过短");
            map.put("msg", "0");
        } else {
            summary = summary.replace("\n", " ");
            String user_id = ((userEntity) request.getSession().getAttribute("user")).getUser_id();
            String blog_id = ((blogEntity) request.getSession().getAttribute("blog")).getBlog_id();
            int res = blogService.saveArticle(article_name, deflaterUtils.zipString(article_text), deflaterUtils.zipString(summary), user_id, blog_id);
            System.out.println("改变行数为:" + res);
            System.out.println("文章上传至数据库成功");
            if (res > 0) {
                map.put("msg", "1");
            } else {
                map.put("msg", "0");
            }
        }
        return objectMapper.writeValueAsString(map);
    }

    @PostMapping("/comment_sub")
    @ResponseBody
    public String comment_handle(HttpServletResponse response,
                                 HttpServletRequest request, @RequestParam("index") String index,
                                 @RequestParam("comment_text") String comment_text) throws IOException {
        Map map = new HashMap();
        if (comment_text.length() > 5) {

            blogEntity blog = (blogEntity) request.getSession().getAttribute("blog");
            userEntity user = (userEntity) request.getSession().getAttribute("user");

            if (blogService.addComment(deflaterUtils.zipString(comment_text), blog.getUser_id(), index, user.getUser_name()) > 0) {
                System.out.println("评论成功!");
                map.put("msg", "1");
            } else {
                System.out.println("评论失败!");
                map.put("msg", "0");
            }
        } else {
            System.out.println("评论过短!");
            map.put("msg", "0");
        }
        return objectMapper.writeValueAsString(map);

    }

    //    上传点赞
    @PostMapping("/praiseSub")
    @ResponseBody
    public String praiseHandle(@RequestParam("praise") String praise,
                               @RequestParam("comment_id") String comment_id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map map = new HashMap();
        if (blogService.subPraise(praise, comment_id) > 0) {
            map.put("praiseMsg", "Praisesuccess");
            return mapper.writeValueAsString(map);
        } else {
            map.put("praiseMsg", "PraiseFailed");
            return mapper.writeValueAsString(map);
        }
    }


}
