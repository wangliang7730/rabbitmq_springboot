package com.atguigu.rabbitmq.three;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * @version 1.0.0
 * @Description:
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/12 13:37
 */
public class Task2 {
    //队列名称
    private static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //让消息队列持久化 但是需要注意的就是如果之前声明的队列不是持久化的，需要把原先队列先删除，或者重新
        //创建一个持久化的队列，不然就会出现错误
        boolean durable=true;
        //声明队列
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);
        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            //设置生产者发送消息为持久化消息（要求保存到磁盘上  ）保存到内存中
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("生产者发出消息"+message);
        }
    }
}
