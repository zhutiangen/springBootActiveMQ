package com.ztg.activemq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

public class DelayConsumer {
    public static final String broker_url = "failover:(tcp://10.1.199.169:61616)";
    private static String queue_name = "test.queue";

    public void consume() throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, broker_url);
        // 通过工厂创建一个连接
        Connection connection = factory.createConnection();
        // 启动连接
        connection.start();
        // 创建一个session会话 事务 自动ack
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        // 创建一个消息队列
        Destination destination = session.createQueue(queue_name);
        // 创建消费者
        MessageConsumer consumer = session.createConsumer(destination);

        // 使用监听 这是异步的
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("receive message ：" + ((TextMessage) message).getText());
                    message.acknowledge(); // 需要告诉客户端，已经接受
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        new CountDownLatch(1).await();
    }
}
