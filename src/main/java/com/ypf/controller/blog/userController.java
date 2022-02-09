package com.ypf.controller.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypf.pojo.userList;
import com.ypf.service.userService;
import com.ypf.utils.jwtToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


@Controller
public class userController {

    @Resource
    userService userService;

    @RequestMapping("/blog/loginUser")
    public String loginUser(HttpSession session ,HttpServletResponse response) {

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
                           @RequestParam("password") String password, HttpSession session) {

        int res = userService.register(user_name, email, password);
        if (res > 0) {
            session.setAttribute("register_res", "注册成功!");
        } else {
            session.setAttribute("register_res", "注册失败!");
        }
        return "blog/registerUser";
    }

    @PostMapping("/blog/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String pwd,
                        @RequestParam("isRemember") String isRemember, HttpSession session, HttpServletResponse response) {



        if(isRemember.equals("on")){
            userList userList = userService.login(email, pwd);
            session.setAttribute("user", userList);
            if (userList != null) {
                if(session.getAttribute("NonUser")!=null){
                    session.removeAttribute("NonUser");
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String json = "";

                try {
                     json= objectMapper.writeValueAsString(userList);
                     response.setContentType("text/html;charset=UTF-8");

                    PrintWriter out =  response.getWriter();
                    out.write("<script>window.localStorage.setItem('token','"+jwtToken.createToken(json)+"')</script>");
                    out.flush();
                    response.flushBuffer();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return "blog/blogIndex";
            }
            else {
                session.setAttribute("NonUser", "用户未登录");
                return "blog/loginUser";

            }


        }
        else {
            userList userList =  userService.login(email,pwd);
            if(userList!=null){
                session.setAttribute("user",userList);
                return "blog/blogIndex";
            }
            else {
                session.setAttribute("NonUser", "登陆失败");
                return "blog/loginUser";
            }



//            try {
//               String user = jwtToken.getUser(isRemember);
//               ObjectMapper objectMapper = new ObjectMapper();
//               userList userList = objectMapper.readValue(user, com.ypf.pojo.userList.class);
//                System.out.println(userList.toString());
//                session.setAttribute("user", userList);
//                return "blog/blogIndex";
//            } catch (Exception e) {
//
//                e.printStackTrace();
//                session.setAttribute("NonUser", "登录已经过期");
//                return "blog/loginUser";
//            }

        }

    }
}
