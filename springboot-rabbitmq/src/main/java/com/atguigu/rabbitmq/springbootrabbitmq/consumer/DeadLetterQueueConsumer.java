package com.atguigu.rabbitmq.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @version 1.0.0
 * @Description:
 * @Author SunBX
 * @Date 2021/7/16 10:37
 */
@Component
@Slf4j
public class DeadLetterQueueConsumer {

    //接受消息
    @RabbitListener(queues = "QD")
    public void receiverQD(Message message, Channel channel){
        String msg=new String(message.getBody());
        log.info("当前时间:{},收到死信消息的队列:{}",new Date().toString(),msg);
    }
}
