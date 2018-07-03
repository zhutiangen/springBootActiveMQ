package com.ztg.activemq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息生产者
 * 类型：p2p类型（点对点） 不使用框架
 * 笔记：1.factory创建connection，
 *      2.connection创建session，
 *      3.session创建Destination 目的地（队列），
 *      4.session和Destination 组合创建Producer生产者
 *      5.session创建消息（message），text/object
 *      6.Producer发送消息
 */
public class JMSProducer {

    /* 定义用户*/
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;

    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    /* 发送的消息数量 */
    private static final int SENDNUM = 10;

    public void start() {

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

        connectionFactory = new ActiveMQConnectionFactory(this.USERNAME, this.PASSWORD, this.BROKEURL);

        try {
            connection = connectionFactory.createConnection();

            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 创建队列
            destination =  session.createQueue("HelloWorld1");

            // 关于队列创建消息的生产者
            messageProducer = session.createProducer(destination);

            // todo 发送消息
            this.sendMessage(session, messageProducer);

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

    /**
     * 发送信息
     * @param session
     * @param messageProducer
     * @throws Exception
     */
    public void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
        for (int i = 0; i < this.SENDNUM; i++) {
            TextMessage textMessage = session.createTextMessage("hhhhhh" + i);
            messageProducer.send(textMessage);
            System.out.println("senactiveMq：" + textMessage.getText());
        }
    }
}
