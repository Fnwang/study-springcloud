package com.b2c.ucenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 绑定登录、注册视图页面
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //跳转登录页面
        registry.addViewController("/uc/login").setViewName("view/login");
        //跳转注册页面
        registry.addViewController("/uc/registry").setViewName("view/registry");
    }
}
