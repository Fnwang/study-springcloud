package com.b2c.ucenter.service;

import com.b2c.ucenter.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    /**
     * 注册
     * @param user
     * @return
     */
    String registry(UserEntity user);
}
