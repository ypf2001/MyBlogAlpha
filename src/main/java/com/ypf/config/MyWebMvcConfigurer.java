package com.ypf.config;

import com.ypf.interceptor.adminLoginIntercept;
import com.ypf.interceptor.blogServiceIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration

public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private adminLoginIntercept adminLoginIntercept;
    @Autowired
    private blogServiceIntercept blogServiceIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(adminLoginIntercept)
               .addPathPatterns("/admin/**")
               .excludePathPatterns("/admin/login")
               .excludePathPatterns("/admin/plugins/**");
       registry.addInterceptor(blogServiceIntercept)
               .addPathPatterns("/blog/**")
               .excludePathPatterns("/")
               .excludePathPatterns("/index")
               .excludePathPatterns("/index.html")
               .excludePathPatterns("/blog/loginUser")
               .excludePathPatterns("/blog/registerUser")
               .excludePathPatterns("/blog/register")
               .excludePathPatterns("/blog/img/**")
               .excludePathPatterns("/static/**")
               .excludePathPatterns("/blog/logout")
               .excludePathPatterns("/blog/autoLogin")
               .excludePathPatterns("/blog/login")
               .excludePathPatterns("/blog/images/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/blog/images/**")
                .addResourceLocations("classpath:/imageStorage/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
