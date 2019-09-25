package com.b2c.b2cprojectlogin.service.impl;

import com.b2c.b2cprojectlogin.entity.MemberEntity;
import com.b2c.b2cprojectlogin.mapper.MemberMapper;
import com.b2c.b2cprojectlogin.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * ImemberService实现类MemberServiceImpl
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements IMemberService
{

}
