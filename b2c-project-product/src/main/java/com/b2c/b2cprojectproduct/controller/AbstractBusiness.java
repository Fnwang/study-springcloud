package com.b2c.b2cprojectproduct.controller;

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
     * 取得用户信息
     */
    public  final  String  SSO_USER_INFO      = "http://sso.shop.com:8081/uc/info";
}
