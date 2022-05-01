package com.ypf.controller.admin;

import com.ypf.pojo.adminList;
import com.ypf.service.adminService;
import com.ypf.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private adminService adminService;

    /*
     *
     * @return
     * */


    /*
     *
     * @return
     * */
    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }


    @PostMapping(value = "/login")
    public String login(
            @RequestParam("admin_name") String admin_name,
            @RequestParam("admin_pwd") String admin_pwd,
            @RequestParam("verifyCode") String verifyCode,
            HttpSession session) {
        if (StringUtils.isEmpty(admin_name) || StringUtils.isEmpty(admin_pwd)) {
            session.setAttribute("errorMessage", "用户名和密码不能为空");
            return "admin/login";

        }
        if(StringUtils.isEmpty(verifyCode)||!verifyCode.equals(session.getAttribute("verifyCode")+"")){

            session.setAttribute("errorMessage", "验证码错误");
            return "admin/login";
        }
        adminList adminList = adminService.login(admin_name, admin_pwd);
        if (adminList != null) {
            String  md5Pwd = MD5Utils.MD5Encode(admin_pwd,"utf-8");
            session.setAttribute("admin_name", adminList.getAdmin_name());
            session.setAttribute("userPwd",md5Pwd);
            return "redirect:/admin/index";
        } else {
            session.setAttribute("errorMessage", "登录失败");
            return "admin/login";
        }

    }
    @GetMapping({"/register"})
    public  String register(){
        return  "admin/register";


    }
    @PostMapping(value="/register")
    public String addAdmin(@RequestParam("admin_name")String admin_name,
                           @RequestParam("admin_pwd") String admin_pwd,
                           @RequestParam("phone") String phone,
//                           HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session) throws IOException {
        adminService.addAdmin(admin_name,admin_pwd,phone);
        return "admin/register";
    }
    @PostMapping(value = "/validateUserName")
    public String validateUserName(@RequestParam("admin_name")String admin_name,HttpServletResponse response,HttpSession session)
            throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, IOException {
       PrintWriter out= response.getWriter();
       if(adminService.findAdmin(admin_name)==null){
            out.print("false");

       }else{
           out.print("true");

       }
       out.flush();
       out.close();
        return "admin/register";
    }


}
