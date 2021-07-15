package com.atguigu.rabbitmq.four;

import com.atguigu.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @version 1.0.0
 * @Description:
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/12 18:18
 */
public class PublishMessageAsync {
    private static final Integer MESSAGE_COUNT=1000;
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,false,false,false,null);
        //开启发布确认
        channel.confirmSelect();
        /**
         *  线程安全有序的一个哈希表，适用于高并发的情况
         *  1.轻松的将序号与消息进行关联
         *  2.轻松的批量删除条目，只要给到序列号
         *  3.支持并发访问
         */

        ConcurrentSkipListMap<Long,String> outstandingConfirms=new ConcurrentSkipListMap<>();

        //开始时间
        long begin = System.currentTimeMillis();

        //消息确认成功，回调函数
        ConfirmCallback ackCallback=(sequenceNumber,multiple)->{
            if (multiple){
                //返回的是小于等于当前序列号的未确认是一个map
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(sequenceNumber, true);
                //清除该部分未确认消息
                confirmed.clear();
            }else{
                //只清除当前序列号的消息
                outstandingConfirms.remove(sequenceNumber);

            }
            System.out.println("确认的消息"+outstandingConfirms);
        };

        /**
         * 1.消息的标记
         * 2.是否为批量确认
         */
        //消息确认失败，回调函数
        ConfirmCallback nackCallback=(consumerTag,multiple)->{
            System.out.println("未确认的消息"+consumerTag);
        };

        /**
         * 准备消息的监听器，监听哪些成功了，哪些失败了
         */
        channel.addConfirmListener(ackCallback,nackCallback);
        for (Integer i = 0; i < MESSAGE_COUNT; i++) {
            String message=i+"";
            channel.basicPublish("",queueName,null,message.getBytes());
        }
        long end = System.currentTimeMillis();
    }
}
