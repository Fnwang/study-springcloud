package com.b2c.b2cprojectproduct.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;
import com.b2c.b2cprojectlogin.comm.*;



/**
 * 产品首页
 */
@Controller
public class ProductViewController extends AbstractBusiness {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 访问
     * @param cookie
     * @return
     */
    @GetMapping("/list")
    public  String index(@CookieValue(required = false ,value = TOKEN )Cookie cookie,
                         HttpSession session ) {

        log.info("cookie信息="+cookie);

        if(cookie!=null) {
            //取cookie值
            String token = cookie.getValue();

            log.info("product 中的 token=>>"+token);

            //伪造或串改token
            //token = token+"3";

            if(StringUtils.isNotEmpty(token)){

                log.info("token有值");

                String apiURL = SSO_USER_INFO+"?token="+token;

                //远程调用用户信息
                Map userInfo = this.restTemplate.getForObject(apiURL, Map.class);

                if(userInfo !=null) {
                    //把用户信息存储到seesion中
                    session.setAttribute(SESSION_ID, userInfo);
                }else{
                    log.info("token验证不通过，没有取到用户信息");
                }
            }
        }else{
            log.info("cookie 没有数据");
            session.removeAttribute(SESSION_ID);
            session.invalidate();
        }
        return  "index";
    }
}
