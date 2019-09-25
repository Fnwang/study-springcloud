package com.b2c.es.service.impl;

import com.b2c.es.mapper.MemberMapper;
import com.b2c.es.model.MemberModel;
import com.b2c.es.service.IMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberModel> implements IMemberService {

}
