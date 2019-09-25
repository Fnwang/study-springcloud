package com.b2c.es.config;

import com.b2c.es.model.MemberModel;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 自定义缓存配置
 */
@Configuration
public class MycaheConfig {

    /**
     * 生成自己的key规则
     * @return
     */
    @Bean("myKeyGenerator")
    KeyGenerator keyGenerator() {
      return  new KeyGenerator() {

            @Override
            public Object generate(Object target, Method method, Object... params) {
                return  method.getName()+"("+ Arrays .asList(params)+")==>"+target.toString();
            }
        };
    }
}
