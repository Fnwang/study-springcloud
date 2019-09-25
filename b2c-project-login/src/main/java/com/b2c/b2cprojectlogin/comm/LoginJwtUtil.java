package com.b2c.b2cprojectlogin.comm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT安全认证工具类
 *
 */
public class LoginJwtUtil {
    /**
     * id
     */
    private static final String USER_ID    = "userId";
    /**
     * login name
     */
    private static final String LOGIN_NAME = "loginName";

    /**
     * typ  key
     */
    private static final String TYP_KEY    = "typ";

    /**
     *
     */
    private static final String TYP_VALUE  = "JWT";
    /**
     *
     */
    private static final String ALG_KEY    = "alg";
    /**
     * 加密方式
     */
    private static final String ALG_VALUE  = "HS256";
    /**
     * 过期时间1小时
     */
    private static final long   EXPIRE_TIME = 1 * 60 * 60 * 1000;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "1gjk%jki^uu79u6f8m067hhd529po0zge8k3$%@k";


    /**
     * 生成token
     * @param userName 用户名
     * @return 加密的token
     */
    public static String createToken(String userName, Integer userId) {
        try {
            // 过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put(TYP_KEY, TYP_VALUE);
            header.put(ALG_KEY, ALG_VALUE);
            // 附带username，userId信息，生成签名
            return JWT.create().withHeader(header)
                    .withClaim(LOGIN_NAME, userName)
                    .withClaim(USER_ID, userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            //System.out.println(jwt.getClaims());
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获取登陆用户ID
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ID).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getLoginName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(LOGIN_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
