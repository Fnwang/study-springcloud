package com.b2c.rabbitmq.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 测试消息队列的监听
 */
@Service
public class RabbitMQListener {


    /**
     * 监听单个队列
     * @param message
     */
    @RabbitListener(queues="q04")
    public void testListener02(Message message) throws UnsupportedEncodingException {
        //打印测试
        System.out.println("getMessageProperties===>："+message.getMessageProperties());
        byte[] bytes = message.getBody();
        String res = new String(bytes,"UTF-8");
        System.out.println("getBody==>："+res);
    }

    /**
     * 实时监听，监听多个队列
     */
    @RabbitListener(queues={"q01","q02","q03"})
    public void testListener01(List<Map<String,Object>> list){
        //打印测试
        System.out.println("发现有数据："+list.toString());
    }

}
