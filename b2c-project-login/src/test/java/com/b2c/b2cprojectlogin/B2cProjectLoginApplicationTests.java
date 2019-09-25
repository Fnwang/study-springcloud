package com.b2c.b2cprojectlogin;

import com.b2c.b2cprojectlogin.controller.LoginController;
import com.b2c.b2cprojectlogin.entity.MemberEntity;
import com.b2c.b2cprojectlogin.entity.UserEntity;
import com.b2c.b2cprojectlogin.service.IMemberService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class B2cProjectLoginApplicationTests {

    @Autowired
    IMemberService imemberService;

    @Autowired
    LoginController loginController;

    /**
     * 查询所有
     */
    @Test
    public void getMemberEntity(){
        List<MemberEntity> list = this.imemberService.list();
        list.forEach(System.out::println);
    }

    /**
     * 测试登录方法
     * @throws Exception
     */
    @Test
    public  void testFindUserByNameAndPassWord() throws Exception
    {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("admin");
        userEntity.setPassWord("123456");

        String rs = loginController.findUserByNameAndPassWord(userEntity,null);


    }

}
