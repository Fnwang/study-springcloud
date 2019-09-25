package com.b2c.b2cprojectlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.b2c.b2cprojectlogin.mapper")
@EnableSwagger2
public class B2cProjectLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cProjectLoginApplication.class, args);
    }

}
