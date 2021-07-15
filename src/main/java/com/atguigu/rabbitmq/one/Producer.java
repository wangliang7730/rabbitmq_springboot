package com.atguigu.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @version 1.0.0
 * @Description:
 * @Author bingxu.sun@evennet.cn
 * @Date 2021/7/11 16:57
 */
public class Producer {
    private static final String  QUERY_NAME="hello";
    public static void main(String[] args) throws Exception{
        //1.创建一个连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //2.工厂ip 连接rabbitMq的队列
        factory.setHost("192.168.96.128");
        //3.用户名
        factory.setUsername("admin");
        //4.密码
        factory.setPassword("123");
        //5.创建连接
        Connection connection = factory.newConnection();
        //6.获取信道
        Channel channel = connection.createChannel();

        /**
         *
         * 生成一个队列
         * 1.队列名称
         * 2.队列里面的的消息是否持久化（磁盘），默认存在内存中
         * 3.改队列是否只供一个消费者进行消费，是否进行消费共享，true可以多个消费者，false只能一个
         * 消费者
         * 4.是否自动删除，最后一个消费者断开连接以后，true自动删除，false不自动删除
         * 5.其他参数
         */

        channel.queueDeclare(QUERY_NAME,false,false,false,null);
        //发消息
        String message="hello world";

        channel.basicPublish("",QUERY_NAME,null,message.getBytes());
        System.out.println("rabbitmq  hello  world");

    }
}
