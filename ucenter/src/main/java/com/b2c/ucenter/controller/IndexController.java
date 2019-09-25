package com.b2c.ucenter.controller;


import com.b2c.ucenter.entity.UserEntity;
import com.b2c.ucenter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录响应
 */
@RestController
public class IndexController {


    @Autowired
    IUserService userService;

    @GetMapping("/personal_center")
    public String login(HttpServletRequest request) {
        return  "登录成功";
    }

    @PostMapping("/registry")
    @ResponseBody
    @CrossOrigin
    public String registry(UserEntity user) {
        return userService.registry (user);
    }
}

