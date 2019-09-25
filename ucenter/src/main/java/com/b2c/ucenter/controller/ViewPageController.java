package com.b2c.ucenter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面跳转
 */
@Controller
public class ViewPageController {

    /**
     * 进入首页
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    /**
     * 客户信息页
     *
     * @return
     */
    @GetMapping("/c/customer")
    public String customer() {
        return "customer.html";
    }

    /**
     * 管理员维护板块
     *
     * @return
     */
    @GetMapping("/admin/setp")
    public String admin_setPassWord() {
        return "admin1.html";
    }

}
