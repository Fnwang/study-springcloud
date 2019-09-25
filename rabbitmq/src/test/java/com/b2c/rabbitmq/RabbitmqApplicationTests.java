package com.b2c.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试springboot rabbitmq
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqApplicationTests {

    //路由类型
    enum ExchangeType {
        headers,direct,fanout,topic
    }


    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 用amqpadmin来创建、删除、管理
     * declare开头，是创建
     * delete开头的，是删除
     */
    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void testEnum(){
        System.out.println(ExchangeType.headers);
    }

    //-------创建路由

    /**
     * 创建路由,direct:点对点
     */
    @Test
    public void createExchangeDirect() {

        Exchange exchange = new DirectExchange("dynamicon.direct");

        this.amqpAdmin.declareExchange(exchange);
    }

    /**
     * 创建路由,fanout：广播
     */
    @Test
    public void createExchangeFanout() {

        Exchange exchange = new FanoutExchange("dynamicon.fanout");

        this.amqpAdmin.declareExchange(exchange);
    }

    /**
     * 创建路由,fanout：发布订阅（主题），支持丰富的配置
     */
    @Test
    public void createExchangeTopic() {

        //Exchange exchange = new TopicExchange("dynamicon.topic");

        Map<String, Object> arguments = new HashMap<>();

        arguments.put("p01", "a");
        arguments.put("p02", "b");
        arguments.put("p03", "c");

        //也可以写成
        Exchange exchange = new CustomExchange(
                "dynamicon.topic01",
                 ExchangeType.topic.name(),
                true,true,arguments);

        this.amqpAdmin.declareExchange(exchange);
    }



    //----------------------创建队列

    /**
     * 创建队列
     */
    @Test
    public void createQueue(){
        this.amqpAdmin.declareQueue(new Queue("dynamicon.queue"));
    }


    /**
     * 绑定
     */
    @Test
    public void binding()
    {
        String destination = "dynamicon.queue";
        String exchange    = "dynamicon.fanout";
        String routingKey  = "dynamicon.queue";
        Map<String, Object> arguments = null;

        Binding binding = new Binding(destination,Binding.DestinationType.QUEUE,exchange,routingKey,arguments);
        this.amqpAdmin.declareBinding(binding);
    }




    /**
     * 发送消息
     */
    @Test
    public void test01(){

        rabbitTemplate.convertAndSend("dyexch.test1","q04", "我来自java调用！");
    }





    /**
     * 接收消息
     */
    @Test
    public  void test02(){
        Object object = rabbitTemplate.receiveAndConvert("q03");
        System.out.println("取回来的结果===>>>>"+object.toString());
    }



    /**
     * 广播式发送
     */
    @Test
    public void test03(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        for(int i=0; i<5;i++) {
            map = new HashMap<>();
            map.put("id", i);
            map.put("name", "王艳"+i);
            map.put("age", 20);
            map.put("address", "重庆合川-"+i);
            list.add(map);
        }
        //发送
        rabbitTemplate.convertAndSend("dyexch.test2", "", list);
    }

}
