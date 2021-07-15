package com.atguigu.rabbitmq.four;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.UUID;

/**
 * @version 1.0.0
 * @Description:
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/12 15:38
 */
public class ConfirmMessage {
    private static final Integer MESSAGE_COUNT=1000;
    public static void main(String[] args) {
        try {
            Channel channel = RabbitMqUtils.getChannel();
            String  queueName = UUID.randomUUID().toString();
            //开启发布确认
            channel.confirmSelect();
            long begin=System.currentTimeMillis();
            for (Integer i = 0; i < MESSAGE_COUNT; i++) {
                String message = i + "";
                channel.basicPublish("",queueName,null,message.getBytes());
                boolean flag = channel.waitForConfirms();
                if(flag){
                    System.out.println("消息发送成功");
                }
            }
            long end = System.currentTimeMillis();
            System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息,耗时" + (end - begin) +
                    "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
