package com.qiqi.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Recv {

    // 队列名称
    private final static String QUEUE_NAME = "delay_queue";
    private final static String EXCHANGE_NAME="delay_exchange";

    public static void main(String[] argv) throws Exception             {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("host");
        factory.setUsername("username");
        factory.setPassword("pass");
        factory.setVirtualHost("vhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


//        channel.queueDeclare(QUEUE_NAME, true,false,false,null);
//        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ""); //将交换器和队列绑定


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                System.out.println("now:\t"+sf.format(new Date()));
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}