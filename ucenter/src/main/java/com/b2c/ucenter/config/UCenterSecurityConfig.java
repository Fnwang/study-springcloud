package com.b2c.ucenter.config;

import com.b2c.ucenter.comment.ROLE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全认证配置
 */
//@Configuration
public class UCenterSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //设置授权规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/c/**").hasRole(ROLE.user.name())
                .antMatchers("/admin/**").hasRole(ROLE.manager.name());

        //打开自带的登录窗口
        //自带的地址访问是/login

        http.formLogin();
        http.rememberMe();
        http.logout ().logoutSuccessUrl ("/");
        //
    }

    /**
     * 测试查询内存数据
     * 查询数据库数据：https://blog.csdn.net/SWPU_Lipan/article/details/80586054
     * @param auth
     * @throws Exception
     */
    @Override

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //1、查内存数据
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zhangsan")
                .password(new BCryptPasswordEncoder().encode("111111"))
                .roles(ROLE.user.name())

                .and()
                .withUser("lisi")
                .password(new BCryptPasswordEncoder().encode("111111"))
                .roles(ROLE.user.name())

                .and()
                .withUser("admin")
                .password(new BCryptPasswordEncoder().encode("111111"))
                .roles(ROLE.user.name(),ROLE.manager.name());

        //2、查询数据库数据
        //auth.userDetailsService(userDetailsService);

    }


}
