package com.b2c.b2cprojectlogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBusiness {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 登录会话
     */
    public  final  String  SESSION_ID = "sessionID";

    /**
     * 来自路径
     */
    public  final  String  URL_TARGET    = "target";

    /**
     * token
     */
    public  final  String  TOKEN  = "token";

    /**
     * 主域
     */
    public  final  String  DO_MAIN       = "shop.com";

    /**
     * 首页路径
     */
    public  final  String  URL_HOME      = "http://sso.shop.com:8081/admin/index";
}
