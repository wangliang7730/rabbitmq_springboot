package com.atguigu.rabbitmq.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0.0
 * @Description: 配置类
 * @Author SunBX
 * @Date 2021/7/16 9:00
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机的名称
    private static final String X_EXCHANGE="X";
    //死信交换机的名称
    private static final String Y_DEAD_LETTER_EXCHANGE="Y";
    //普通队列的名称
    private static final String QUEUE_A="QA";
    private static final String QUEUE_B="QB";
    private static final String QUEUE_C="QC";
    //死信队列的名称
    private static final String DEAD_LETTER_QUEUE="QD";

    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    @Bean("queueA")
    public Queue queueA(){
        Map<String,Object> map=new HashMap<>(3);
        //设置死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置死信RoutingKey
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL  单位是ms
        map.put("x-message-ttl",10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(map).build();
    }

    @Bean("queueB")
    public Queue queueB(){
        Map<String,Object> map=new HashMap<>(3);
        //设置死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置死信RoutingKey
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL  单位是ms
        map.put("x-message-ttl",40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(map).build();
    }

    @Bean("queueC")
    public Queue queueC(){
        Map<String,Object> map=new HashMap<>(3);
        //死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置死信routingkey
        map.put("x-dead-letter-routing-key","YD");

        return QueueBuilder.durable(QUEUE_C).withArguments(map).build();
    }
    @Bean("queueD")
    public Queue queueD(){
        Map<String,Object> map=new HashMap<>(3);
        //设置死信交换机
        map.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        //设置死信RoutingKey
        map.put("x-dead-letter-routing-key","YD");
        //设置TTL  单位是ms
        map.put("x-message-ttl",40000);
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }
    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange")DirectExchange xExchange){

        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    //绑定
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }
    //绑定
    @Bean
    public Binding queueABindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange")DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
    //绑定
    @Bean
    public Binding queueCBindingC(@Qualifier("queueC")Queue queueC,
                                  @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
}
