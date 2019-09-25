package com.b2c.es;

import com.b2c.es.mapper.MemberMapper;
import com.b2c.es.model.MemberModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

/**
 * 测试redis缓存
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    //对应日志
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate<Object, MemberModel> myRedisTemplate;


    /**
     * 取key为msg的值
     */
    @Test
    public void testRedis01(){
        String msg = stringRedisTemplate.opsForValue().get("msg");

        logger.info("msg==>"+msg);
    }

    /**
     * 以json格式存储数据
     */
    @Test
    public void setData(){

        List<MemberModel> list = memberMapper.selectList(null);
        for (MemberModel indexMember:list) {
            myRedisTemplate.opsForSet().add("member", indexMember);
        }
        //myRedisTemplate.opsForValue().set("m01", memberModel);
    }

    @Test
    public void getData(){
        Set<MemberModel> memberModel =  myRedisTemplate.opsForSet().members("member");
        memberModel.forEach(System.out::println);
    }

    /**
     * 测试线程
     * @throws InterruptedException
     */
    @Test
    public void testThread() {
        logger.info("执行本方法===》");
        dorun();
    }

    @Async
    public void dorun() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }
        logger.info("执行 run ……");
    }
}
