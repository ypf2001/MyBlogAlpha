package com.ypf.config;

import com.ypf.interceptor.adminLoginIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration

public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private adminLoginIntercept adminLoginIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(adminLoginIntercept)
//               .addPathPatterns("/admin/**")
//               .excludePathPatterns("/admin/login")
//               .excludePathPatterns("/admin/plugins/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
