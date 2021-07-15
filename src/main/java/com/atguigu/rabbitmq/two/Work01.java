package com.atguigu.rabbitmq.two;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @version 1.0.0
 * @Description: 工作线程
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/11 17:52
 */
public class Work01 {
    //对列的名称
    private static  final String QUEUE_NAME="hello";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("接受到的消息"+new String(message.getBody()));
        };

        CancelCallback cancelCallback=consumerTag->{
            System.out.println("消息被消费者取消消费接口回调逻辑");
        };
        System.out.println("c1等待接受消息......");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
