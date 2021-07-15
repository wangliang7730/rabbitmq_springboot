package com.atguigu.rabbitmq.five;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @version 1.0.0
 * @Description:
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/13 14:20
 */
public class ReceiveLogs02 {
    private static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        /**
         * 生成一个临时队列 队列的名称是随机的
         * 当消费者断开和该队列的连接时 队列自动删除
         *
         */
        String queueName = channel.queueDeclare().getQueue();
        // 把该临时队列绑定我们的exchange 其中routingkey
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("等待接受消息，把接受到的消息打印在屏幕上");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("控制台打印接收到的消息"+message);
        };
        channel.basicConsume(queueName,true,deliverCallback,consumerTag -> { });
    }
}
