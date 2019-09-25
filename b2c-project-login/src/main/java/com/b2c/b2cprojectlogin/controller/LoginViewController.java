package com.b2c.b2cprojectlogin.controller;

import com.b2c.b2cprojectlogin.comm.LoginCacheUtil;
import com.b2c.b2cprojectlogin.entity.UserEntity;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 进入登录页面
 */
@Controller
@RequestMapping("/admin/")
public class LoginViewController  extends  AbstractBusiness {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 进入后台首页
     * @return
     */
    @GetMapping("index")
    public String indexPage(){

        //返回页面
        return "index";

    }

    /**
     * 控制台首页（欢迎页面）
     * @return
     */
    @GetMapping("main")
    public String mainPage(){

        //返回页面
        return "main";

    }

    /**
     * 进入登录页面
     * @param target    涞源
     * @param cookie    用于判断是否已经登录过，如果已经登录，直接转向管理页面
     * @param session   用于存储涞源地址
     *
     * @return
     */
    @RequestMapping("login")
    public String loginPage(@RequestParam(required = false,  defaultValue = "") String target,
                            @CookieValue( required = false,  value = TOKEN) Cookie cookie,
                            HttpSession session)
    {

        log.info(" 登录页面 cookie==>>>>"+cookie);

        if (StringUtils.isEmpty(target)) {
            target = super.URL_HOME;
        }

        if(cookie !=null) {
            //取cookie值
            String token = cookie.getValue();

            log.info(" 登录页面 token==>>>>"+token);
            //取登录用户信息
            UserEntity userEntity = LoginCacheUtil.LOGIN_USER.get(token);
            log.info(" userEntity==>>>>"+userEntity);
            //如果已经存在，不能进入登录页面。
            if (userEntity != null) {
                return "redirect:" + target;
            }
        }
        //把target放到会话中
        session.setAttribute(super.URL_TARGET, target);
        //返回页面
        return "/views/login";

    }

    /**
     * 进入用户中心
     * @return
     */
    @GetMapping("user")
    public String userPage(){
        //返回页面
        return "/views/user/profile";

    }
}
