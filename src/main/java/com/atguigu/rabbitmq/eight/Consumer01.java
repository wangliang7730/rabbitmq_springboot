package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0.0
 * @Description:
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/15 8:59
 */
public class Consumer01 {
    //普通交换机名称
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机名称
    private static final String DEAD_EXCHANGE = "dead_exchange";
    //普通队列名称
    private static final String NORMAL_QUEUE = "normal_queue";
    //死信队列名称
    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明死信和普通交换机 类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明普通队列
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,null);
        /* 在生产者设置更灵活可以随意修改过期时间,消费者这边设置不能修改
        Map<String,Object> arguments=new HashMap<>();
        arguments.put("x-message-ttl",100000);
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","lisi");
        */
        
        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE,false,false,false,arguments);

        //绑定普通的交换机与普通的队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        //绑定死信的交换机与死信的队列
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"zhangsan");

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        };
        channel.basicConsume(NORMAL_QUEUE,true,deliverCallback,consumerTag->{});
    }
}
