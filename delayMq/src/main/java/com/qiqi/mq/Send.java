package com.qiqi.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Send {
    // 队列名称
    private final static String EXCHANGE_NAME = "delay_exchange";
    private final static String ROUTING_KEY = "";

    @SuppressWarnings("deprecation")
    public static void main(String[] argv) throws Exception {
        /**
         * 创建连接连接到MabbitMQ
         */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("host");
        factory.setUsername("username");
        factory.setPassword("pass");
        factory.setVirtualHost("vhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        // 声明x-delayed-type类型的exchange
//        Map<String, Object> args = new HashMap<String, Object>();
//        args.put("x-delayed-type", "direct");
//        channel.exchangeDeclare(EXCHANGE_NAME, "x-delayed-message", true,
//                false, args);

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("x-delay", 5*1000);
        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder()
                .headers(headers);

        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, props.build(),
                "delay message".getBytes());

        System.out.println("now:\t"+sf.format(new Date()));

        // 关闭频道和连接
        channel.close();
        connection.close();
    }
}