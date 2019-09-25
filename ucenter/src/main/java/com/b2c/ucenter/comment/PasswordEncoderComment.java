package com.b2c.ucenter.comment;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 加密
 */
@Component
public class PasswordEncoderComment {

    //使用构造方法生成对象
    private  PasswordEncoder passwordEncoder;

    public PasswordEncoderComment() {
        //密码加密
        String idForEncode = "dynamicon";
        Map encoders = new HashMap<> ();
        encoders.put(idForEncode, new BCryptPasswordEncoder ());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder ());
        encoders.put("scrypt", new SCryptPasswordEncoder ());
        encoders.put("sha256", new StandardPasswordEncoder ());

         passwordEncoder = new DelegatingPasswordEncoder (idForEncode, encoders);

    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
