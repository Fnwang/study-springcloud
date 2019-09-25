package com.b2c.b2cprojecthome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 打开首页
 */
@Controller
public class IndexViewController {

    @GetMapping("/")
    public  String index() {
        return  "index";
    }
}
