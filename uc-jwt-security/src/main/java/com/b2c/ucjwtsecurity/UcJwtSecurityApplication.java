package com.b2c.ucjwtsecurity;

import com.b2c.ucjwtsecurity.util.ApplicationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UcJwtSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(UcJwtSecurityApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationUtil applicationUtil() {
        return new ApplicationUtil();
    }

}
