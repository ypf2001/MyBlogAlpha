package com.ypf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.ypf.dao")
@SpringBootApplication
public class MyBlogAlphaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBlogAlphaApplication.class, args);
    }

}
