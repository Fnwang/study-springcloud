package com.b2c.ucenter.config;

import com.b2c.ucenter.comment.PasswordEncoderComment;
import com.b2c.ucenter.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * security安装认证配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoderComment PasswordEncoderComment;

    @Bean
    UserDetailsService detailsService() {
        return new UserServiceImpl ();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println ("获取用于信息==》");
        auth.userDetailsService(detailsService()).passwordEncoder(PasswordEncoderComment.getPasswordEncoder ());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring ().antMatchers ("/config/**", "/css/**", "/fonts/**", "/img/**", "/js/**");
    }

    /**
     * 登录配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/uc/**").permitAll ()
                .and ().formLogin ().loginPage ("/uc/login")
                .loginProcessingUrl ("/login").defaultSuccessUrl ("/", true)
                .failureUrl ("/uc/login?error").permitAll ()
                .and ().sessionManagement ().invalidSessionUrl ("/uc/login")
                .and ().rememberMe ().tokenValiditySeconds (1209600)
                .and ().logout ().logoutSuccessUrl ("/").permitAll ()
                .and ().csrf ().disable ();

        /*
        http.headers ()
                .and ().authorizeRequests ()
                .antMatchers ("/view/registry").permitAll ()
                .anyRequest ().authenticated ()
                .and ().formLogin ().loginPage ("/uc/login")
                .loginProcessingUrl ("/login").defaultSuccessUrl ("/personal_center", true)
                .failureUrl ("/uc/login?error").permitAll ()
                .and ().sessionManagement ().invalidSessionUrl ("/uc/login")
                .and ().rememberMe ().tokenValiditySeconds (1209600)
                .and ().logout ().logoutSuccessUrl ("/uc/login").permitAll ()
                .and ().csrf ().disable ();
                */
    }
}