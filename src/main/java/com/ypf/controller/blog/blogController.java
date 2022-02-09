package com.ypf.controller.blog;

import com.ypf.service.blogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class blogController {
    @Resource
    blogService blogService;

    @GetMapping({"/","/index","/index.html"})
    public String index(){return "blog/index";
    }
    @PostMapping("/blogIndex")
    public String validateUser(
            @RequestParam("email") String email,
            @RequestParam("password") String Password,
            HttpSession session
    ){



        return "blog/blogIndex";
    }
    @RequestMapping({"/userCenter"})

    public  String userCenter(){return  "blog/userCenter";}
    @PostMapping ({"/dataSubmit"})
    public  String upload_article(@RequestParam("article_name")String article_name,
                                  @RequestParam("article_text")String article_text,
                                  @RequestParam("comment_id")String comment_id ,
                                  @RequestParam("summary") String summary, HttpServletResponse servletResponse){
        PrintWriter out=null;

        try {
            out = servletResponse.getWriter();

        if(comment_id == null ||comment_id.length()==0){comment_id = "0";}
        if (summary == null || summary.length()==0){summary="null";}
          if(article_name==null||article_name.length()==0){
            out.print("标题不能为空");
              System.out.println("标题不能为空");

        }
        else if(article_text.length()<50){
            out.print("文章字数不能小于50");

        }
        else {
             try {
                 blogService.saveArticle(article_name, article_text, comment_id, summary);
                 out.write("文章上传成功");
                 System.out.println("上传至数据库成功");
             } catch (Exception e ){
                 System.out.println(e.getMessage());
             }
         }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.flush();
            out.close();
        }



        return  "blog/userCenter";
    }
}
