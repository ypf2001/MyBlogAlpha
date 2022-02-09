package com.ypf.myblog_alpha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypf.pojo.adminList;
import com.ypf.pojo.userList;
import com.ypf.utils.jwtToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class MyBlogAlphaApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void CliamTest () throws JsonProcessingException {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
ObjectMapper objectMapper = new ObjectMapper();
        adminList aa = new adminList();
        aa.setAdmin_id("4");
        aa.setAdmin_name("ypf");
        aa.setAdmin_pwd("123456");
        aa.setCreate_time(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        String aaStr =  objectMapper.writeValueAsString(aa);
        String user = "{\n" +
                "    \"sites\": [\n" +
                "    { \"name\":\"菜鸟教程\" , \"url\":\"www.runoob.com\" }, \n" +
                "    { \"name\":\"google\" , \"url\":\"www.google.com\" }, \n" +
                "    { \"name\":\"微博\" , \"url\":\"www.weibo.com\" }\n" +
                "    ]\n" +
                "}";
        try {
            String token = jwtToken.createToken(aaStr);

            System.out.println("user是:"+jwtToken.getUser(token));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
