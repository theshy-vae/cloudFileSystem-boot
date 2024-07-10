package com.hyj.cloud.config;

import com.hyj.cloud.interceptor.ResponseResultInterceptor;
import com.hyj.cloud.interceptor.UserInfoInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
public class WebResultConfig implements WebMvcConfigurer {

    // SpringMVC 需要手动添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath=new ArrayList<>();
        excludePath.add("/user/register");
        excludePath.add("/user/login");
        excludePath.add("/user/verifyToken");

        registry.addInterceptor(new ResponseResultInterceptor());
        registry.addInterceptor(new UserInfoInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

