package com.atguigu.rabbitmq.one;

import com.rabbitmq.client.*;

/**
 * @version 1.0.0
 * @Description: 消费者
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/11 17:35
 */
public class Consumer {
    private static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception{
        //1.创建连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("192.168.96.128");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        //声明 接受消息
        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        };
        //取消消息时的回调
        CancelCallback cancelCallback=consumerTag->{
            System.out.println("消息被中断");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
