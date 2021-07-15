package com.atguigu.rabbitmq.three;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.atguigu.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @version 1.0.0
 * @Description:
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/12 13:51
 */
public class Work04 {
    //队列名称
    private static final String TASK_QUEUE_NAME="ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback=(consumerTag,message)->{
            System.out.println("接受到的消息："+new String(message.getBody()));
            System.out.println("C2 等待接收消息处理时间较短");
            SleepUtils.sleep(30);
            /**
             * 1.消息标记 tag
             * 2.是否批量应答未应答消息
             */

            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        //0:默认轮询的
        //1:不公平分发
        int prefetchCount=1;
        channel.basicQos(prefetchCount);
        //采用手动应答
        boolean autoAck=false;
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,deliverCallback,consumerTag->{
            System.out.println(consumerTag+"消费者取消消费接口回调逻辑");
        });
    }
}
