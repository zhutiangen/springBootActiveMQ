package com.ztg.activemq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

public class DelayProducer {

    private static final String broker_url = "failover:(tcp://10.1.199.169:61616)";

    private static String queue_name = "test.queue";

    public void produce() {

        /* 连接工厂 */
        ConnectionFactory connectionFactory;

        /* 连接 */
        Connection connection = null;

        /* 会话 */
        Session session;

        /* 消息目的  队列（Queue和Topic都是实现ta的）*/
        Destination destination;

        /* 消息生产者 */
        MessageProducer messageProducer;

        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);

        try {
            connection = connectionFactory.createConnection();

            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建队列
            destination =  session.createQueue("HelloWorld1");

            // 关于队列创建消息的生产者
            messageProducer = session.createProducer(destination);
            // 消息持久化
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            // todo 发送消息
            TextMessage message = session.createTextMessage("test delay message: " + System.currentTimeMillis());
            long time = 60 * 1000;// 延时1min
            long period = 10 * 1000;// 每个10s
            int repeat = 6;// 6次

            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
            message.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);

            messageProducer.send(message);
            // 提交事务
            session.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
