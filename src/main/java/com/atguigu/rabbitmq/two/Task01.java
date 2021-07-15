package com.atguigu.rabbitmq.two;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @version 1.0.0
 * @Description: 生产者
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/11 18:06
 */
public class Task01 {
    //对列的名称
    private static  final String QUEUE_NAME="hello";
    //发送大量消息
    public static void main(String[] args) throws Exception {
        //通道
        Channel channel = RabbitMqUtils.getChannel();
        //队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("消息发送完成"+message);
        }
    }
}
