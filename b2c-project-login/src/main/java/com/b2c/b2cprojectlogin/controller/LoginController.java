package com.b2c.b2cprojectlogin.controller;

import com.b2c.b2cprojectlogin.comm.LoginCacheUtil;
import com.b2c.b2cprojectlogin.comm.LoginJwtUtil;
import com.b2c.b2cprojectlogin.entity.UserEntity;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 用户登录接口
 */
@Api(value = "用户管理模块", description = "用户管理模块的API描述")
@RestController
@RequestMapping("/uc/")
public class LoginController extends  LoginBusiness {


    /**
     * 登录
     * @return
     */
    @ApiOperation(value = "登录接口",notes="登录接口:传递UserEntity参数" )

    @PostMapping("doLogin")
    @ResponseBody
    @CrossOrigin
    public  String doLogin(
            @ApiParam(value = "UserEntity实例化参数") UserEntity userEntity,
            @ApiParam(value = "HttpSession，无需传值") HttpSession session,
            @ApiParam(value = "HttpServletResponse，无需传值") HttpServletResponse response)
    {
        String rs = null;
        String target = super.URL_HOME ;
        //判断从那里来的
        if (session.getAttribute(super.URL_TARGET) !=null) {
            target = (String)session.getAttribute(super.URL_TARGET);
        }
        try {
             //查找用户
             rs = super.findUserByNameAndPassWord(userEntity,target);

             //查找成功
             if(super.loginSuccess) {
                 //生成token
                 //String token = UUID.randomUUID().toString();

                 //调用LoginJwtUtil生成加密后的token
                 String token = LoginJwtUtil.createToken(userEntity.getUserName(),userEntity.getId());

                 log.info("LoginJwtUtil生成加密后的token==>"+token);

                 //设置cookie
                 Cookie cookie = new Cookie(TOKEN, token);
                 //设置同一个域
                 cookie.setDomain(super.DO_MAIN);
                 cookie.setPath("/");
                 cookie.setHttpOnly(true);
                 cookie.setMaxAge(20*60);
                 //添加cookie
                 response.addCookie(cookie);
                 //添加用户到map中
                 LoginCacheUtil.LOGIN_USER.put(token,userEntity);
                 //设置回话
                 session.setAttribute(SESSION_ID, rs);
             }

        }catch (Exception e){

        }

        return rs;
    }


    /**
     * 退出系统
     * @param target
     * @param session
     * @param response
     * @return
     */
    @ApiOperation(value = "用户退出接口")
    @GetMapping("signOut")
    public void signOut(
            @ApiParam(value = "请求涞源，注销后返回到原来到地址") @RequestParam(required = false, defaultValue = "") String target,
                        HttpSession session,
                        HttpServletRequest request,
                        HttpServletResponse response)
    throws Exception  {

        log.info("退出系统：");
        //查找cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            // 遍历浏览器发送到服务器端的所有Cookie，找到自己设置的Cookie
            for (Cookie cookie : cookies) {
                String token = cookie.getName();
                if (token.equals(TOKEN)) {
                    // 设置Cookie立即失效
                    cookie.setMaxAge(0);
                    /**
                     * 删除Cookie时，只设置maxAge=0将不能够从浏览器中删除cookie,
                     * 因为一个Cookie应当属于一个path与domain，所以删除时，Cookie的这两个属性也必须设置。
                     *
                     * 误区:刚开始时，我没有发现客户端发送到服务器端的cookie的path与domain值为空这个问题。
                     * 因为在登陆系统时，我设置了Cookie的path与domain属性的值,就误认为每次客户端请求时，都会把Cookie的
                     * 这两个属性也提交到服务器端，但系统并没有把path与domain提交到服务器端(提交过来的只有Cookie的key，value值)。
                     */
                    // 重点是这里,必须设置domain属性的值
                    cookie.setDomain(super.DO_MAIN);
                    // 重点是这里2,必须设置path属性的值
                    cookie.setPath("/");
                    response.addCookie(cookie);

                    //清除会话
                    session.removeAttribute(SESSION_ID);
                }
            }
        }


        //跳转
        if (StringUtils.isEmpty(target)) {
            target = super.URL_HOME;
        }

        response.sendRedirect(target);

    }


    /**
     * 通过token获取用户登录信息
     * @param token
     * @return
     */
    @ApiOperation(value = "通过token获取用户信息接口")
    @GetMapping("info")
    @ResponseBody
    public ResponseEntity<UserEntity> getLoginUserInfo(
            @ApiParam(value = "客户端发送来到token值") String token) {

        if (StringUtils.isNotEmpty(token)) {

            log.info("请求的token==>"+token);

            //验证token,如果token合法
           if( LoginJwtUtil.verifyToken(token)){

               log.info("请求的token合法");

               UserEntity userEntity = LoginCacheUtil.LOGIN_USER.get(token);

               //获取用户信息
               if(userEntity!=null) {
                   return ResponseEntity.ok(userEntity);
               }else{
                   return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
               }
           }
           //token不合法
           else {

               log.info("请求的token不合法");
               return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
           }

        }else {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
