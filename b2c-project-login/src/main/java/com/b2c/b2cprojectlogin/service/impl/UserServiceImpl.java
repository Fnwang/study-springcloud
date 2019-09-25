package com.b2c.b2cprojectlogin.service.impl;

import com.b2c.b2cprojectlogin.entity.UserEntity;
import com.b2c.b2cprojectlogin.mapper.UserMapper;
import com.b2c.b2cprojectlogin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 服务
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {
}
