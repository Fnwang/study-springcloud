package com.b2c.b2cprojectlogin;

import com.b2c.b2cprojectlogin.comm.PageUtil;
import com.b2c.b2cprojectlogin.comm.ResultData;
import com.b2c.b2cprojectlogin.controller.MemberController;
import com.b2c.b2cprojectlogin.entity.MemberEntity;
import com.b2c.b2cprojectlogin.mapper.MemberMapper;
import com.b2c.b2cprojectlogin.service.IMemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberController memberController;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    IMemberService iMemberService;

    /**
     * 测试查询会员列表
     */
    @Test
    public void testGetMemberEntity()  {

        String rs = memberController.getMemberEntity("",1);

    }

    @Test
    public void test01() throws  Exception {

        long indexNo = 2;
        long size    = 20;

        //查询条件
        //有Lambda
        LambdaQueryWrapper<MemberEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
        //lambdaQueryWrapper.like(MemberEntity::getCity, "");

        /*
        //分页类
        Page<MemberEntity>  page   = new Page();
        page.setCurrent(1);
        page.setSize(20);

        IPage<MemberEntity> ipage  = memberMapper.selectPage(page,lambdaQueryWrapper);
        //取结果
        List<MemberEntity> list = ipage.getRecords();
        System.out.println("总记录："+ipage.getTotal());
        System.out.println("总页数："+ipage.getPages());
        list.forEach(System.out::println);
        */

        //
        PageUtil<MemberEntity> pageUtile = new PageUtil(indexNo,iMemberService,lambdaQueryWrapper,size);

        //
        Map<String,Object> userData = new HashMap<String,Object>();
        List<String> userList = new ArrayList<String>();
        userList.add("张三");
        userList.add("李四");
        userList.add("王五");

        JSONObject js = new JSONObject();
        userData.put("userData",new JSONArray(userList));

        JSONObject json = new ResultData().getResultData(pageUtile.getRecords(), pageUtile.getTotal(), "200", "调用成功", userData);

        log.info(json.toString());
    }
}
