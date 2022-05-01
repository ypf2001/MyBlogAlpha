package com.ypf.controller.blog;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypf.entity.blogEntity;
import com.ypf.entity.userEntity;
import com.ypf.service.blogService;
import com.ypf.service.impl.redisUtils;
import com.ypf.service.userService;
import com.ypf.utils.Base64Method;
import com.ypf.utils.jsoupUtils;
import com.ypf.utils.jwtToken;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ypf.utils.deflaterUtils;


@Controller
public class userController {
    @Resource
    ObjectMapper objectMapper;
    @Resource
    redisUtils redisUtils;
    @Resource
    userService userService;
    @Resource
    blogService blogService;
    @Resource
    Logger logger;

    @RequestMapping("/blog/userCenter")
    public String userCenter(HttpSession session, Model model) {
        blogEntity blog = ((blogEntity) session.getAttribute("blog"));
        model.addAttribute("article_num", blogService.getArticleNum(blog.getBlog_id()));
        return "blog/userCenter";
    }

    @PostMapping("/blog/updateArticleList")
    public String updateArticleList(HttpSession session, @RequestParam("pageIndex") int pageIndex, Model model) {
        initArticleList(((blogEntity) session.getAttribute("blog")).getBlog_id(), model, pageIndex);
        return "frag/centerFrag::rightBody";
    }

    @RequestMapping(value = "/blog/userIndex", method = RequestMethod.GET)
    public String userIndexParse(@RequestParam("user_id") String user_id, Model model, HttpSession session) {
        String key = "MyBlog:" + ((userEntity) session.getAttribute("user")).getUser_name() + ((userEntity) session.getAttribute("user")).getUser_id() + ":subId";
        AtomicBoolean flag = new AtomicBoolean(false);
        if (((userEntity) session.getAttribute("user")).getUser_id() != user_id) {
            redisUtils.lGetAll(key).forEach(item -> {
                if (item.toString().equals(user_id)) {
                    flag.set(true);
                }
            });
        }
        model.addAttribute("UserBlogArticle", userService.getUserBlogArticle(user_id));
        model.addAttribute("isSub", flag.get());
        return "blog/userIndex";
    }

    @RequestMapping({"/", "/index", "/index.html"})
    public String blogIndex(HttpSession session){
        try {
            session.setAttribute("weatherMsg", jsoupUtils.getLocalWeather());
            logger.info(jsoupUtils.getLocalWeather().toString());
        } catch (Exception e) {
           logger.error("jsoup解析失败");
        }

        return "blog/blogIndex";
    }

    @RequestMapping("/blog/loginUser")
    public String loginUser(HttpSession session, HttpServletResponse response) {

        return "blog/loginUser";
    }

    @RequestMapping("/blog/registerUser")
    public String registerLogin() {

        return "blog/registerUser";
    }

    @RequestMapping("/blog/forgot")
    public String forgot() {

        return "blog/forgot";
    }

    @RequestMapping("/blog/resetUser")
    public String resetUser() {

        return "blog/resetUser";
    }

    @PostMapping("/blog/register")

    public String register(@RequestParam("user_name") String user_name,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password) {

        int res = userService.register(user_name, email, password);

        //自动开通博客
        //默认博客名为
        int createBlog = blogService.createBlog(user_name + "的博客", "0", "", "写点什么吧！");
        if (res > 0) {
            logger.info(user_name+"注册成功");
            return "/";
        }

        return "blog/registerUser";
    }


    @PostMapping("/blog/autoLogin")
    @ResponseBody
    public String autoLogin(@RequestParam("token") String token, HttpServletResponse response, HttpSession session, Model model) throws Exception {
            if(session.getAttribute("user")!=null&&session.getAttribute("blog")!=null){
                return "{\"msg\":\"0\"}";
            }else{
                if (token != null) {
                    userEntity userEntity = objectMapper.readValue(jwtToken.getUser(token), userEntity.class);
                    blogEntity blogEntity = blogService.getBlog(userEntity.getUser_id());

                    session.setAttribute("user", userEntity);
                    session.setAttribute("blog", blogEntity);
                    session.setAttribute("userLogo", objectMapper.readValue(userEntity.getLogo(), Map.class));

                    System.out.println(userEntity.getUser_name() + "登录成功");
                    return "{\"msg\":\"1\"}";
                }
                return "{\"msg\":\"0\"}";
            }

    }

    @PostMapping("/blog/login")
    @ResponseBody
    public String login(@RequestParam("email") String email, @RequestParam("password") String pwd,
                        @RequestParam("isRemember") String isRemember, HttpSession session, HttpServletResponse response, Model model) throws Exception {
       Map<Object, Object> map = new HashMap<>();
        userEntity userEntity = userService.login(email, pwd);
        blogEntity blogEntity = blogService.getBlog(userEntity.getUser_id());
        map.put("msg",0);
        if(userEntity!=null){
            session.setAttribute("user", userEntity);
            session.setAttribute("blog", blogEntity);
            map.put("msg",1);
        }
//                生成jwtToken并写入浏览器
         String json = "";
            json = objectMapper.writeValueAsString(userEntity);
            //判断是否记住密码
            if (isRemember.equals("on")) {
                map.put("token", jwtToken.createToken(json));
            }

            return objectMapper.writeValueAsString(map);
    }

    @PostMapping("/canvasHandle")
    @ResponseBody
    public String canvasHandle(@RequestParam("canvasData") String canvasData,
                               @RequestParam("isChange") String isChange,
                               @RequestParam("mainData") String mainData,
                               @RequestParam("extension") String extension,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<Object, Object> map = new HashMap<>();
//      判断是否裁剪图片
        if (isChange.contentEquals("false")) {
            System.out.println("图片未裁剪");
            map.put("token", "null");
            return objectMapper.writeValueAsString(map);
        } else {
            String token = null;
            String dirPath = "\\imageStorage\\" + ((userEntity) request.getSession().getAttribute("user")).getUser_name();
            String mainImgPath = dirPath + "\\body" + extension;
            System.out.println(mainImgPath);

            System.out.println(dirPath);
            File f = new File(dirPath);
            if (!f.exists()) {
                f.mkdir();
//            第一次创建文件夹名为user_name
                ((userEntity) request.getSession().getAttribute("user"))
                        .setLogo(dirPath.substring(dirPath.lastIndexOf("\\") + 1));
                System.out.println(((userEntity) request.getSession().getAttribute("user"))
                        .getLogo());
                System.out.println("创建成功");

            }

//        将logo写入数据库
            userEntity userEntity = ((userEntity) request.getSession().getAttribute("user"));
            map.put("mainBody", dirPath.substring(dirPath.lastIndexOf("\\") + 1) + "\\body" + extension);
            map.put("canvas", dirPath.substring(dirPath.lastIndexOf("\\") + 1) + "\\canvas" + extension);
            String distName = objectMapper.writeValueAsString(map);
            userEntity.setLogo(distName);
            request.getSession().setAttribute("user", userEntity);
            request.getSession().setAttribute("userLogo", map);
            userService.updateLogo(distName, userEntity.getUser_id());
            token = jwtToken.createToken(objectMapper.writeValueAsString(userEntity));
            //            更新jwtToken

            System.out.println("logo_dir已经存在");
//        裁剪写入文件
            Base64Method.base64ToImage(mainData, mainImgPath);
            Base64Method.base64ToImage(canvasData, dirPath + "\\canvas" + extension);
            System.out.println("图片创建成功");
            map.put("token", token);
            return objectMapper.writeValueAsString(map.get("token"));
        }
    }

    @PostMapping("/subHandle")
    @ResponseBody
    public String subHandle(@RequestParam("sub_id") String sub_id, HttpSession session) throws IOException {
        Map<String, Object> map = new HashMap();

        List list = new ArrayList();
        String key = "MyBlog:" + ((userEntity) session.getAttribute("user")).getUser_name() + ((userEntity) session.getAttribute("user")).getUser_id() + ":subId";
        AtomicBoolean flag = new AtomicBoolean(true);
        redisUtils.lGetAll(key).forEach(item -> {
            if (item.toString().equals(sub_id)) {
                redisUtils.lRemove(key, 1, sub_id);
                flag.set(false);
            }
        });
        if (flag.get()) {
            if (redisUtils.lSet(key, sub_id)) {
                map.put("msg", "1");
            } else {
                map.put("msg", 0);
            }
        } else {
            map.put("msg", 0);
        }


        return objectMapper.writeValueAsString(map);
    }

    void initArticleList(String blog_id, Model model, int pageIndex) {
        List<blogEntity> blogEntities = blogService.findArticleMsg(blog_id, pageIndex);
        System.out.println(blogEntities);
        if (blogEntities != null && blogEntities.size() > 0) {
            blogEntities.get(0).getArticleCollections().forEach(
                    item -> {
                        String tempStr = item.getSummary();
                        try {
                            item.setSummary(deflaterUtils.unzipString(tempStr));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
        }

        model.addAttribute("articleMsg", blogEntities);
    }
}
