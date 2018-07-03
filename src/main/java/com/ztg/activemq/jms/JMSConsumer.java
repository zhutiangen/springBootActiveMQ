package com.ztg.activemq.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息的消费者
 */
public class JMSConsumer {

    private static final String UserName = ActiveMQConnection.DEFAULT_USER;

    private static final String Password = ActiveMQConnection.DEFAULT_PASSWORD;

    private static final String brokeUrl = ActiveMQConnection.DEFAULT_BROKER_URL;

    public void init() {

        ConnectionFactory connectionFactory;

        Connection connection = null;

        Session session;

        Destination destination;

        MessageConsumer messageConsumer;

        try {
            connectionFactory = new ActiveMQConnectionFactory(this.UserName, this.Password, this.brokeUrl);

            connection = connectionFactory.createConnection();
            connection.start();

            /**
             * true: 表示支持是事物，参数二无效； false：表示不支持事物
             *
             * param2：Session.AUTO_ACKNOWLEDGE；表示自动确认，客户端发送信息和接受信息不需要做额外的工作。
             *         Session.CLIENT_ACKNOWLEDGE：客户端接受信息，必须调用javax.jms.Message的acknowledge方法，jms服务器才会删除信息
             *         Session.DUPS_OK_ACKNOWLEDGE：允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，
             *                  会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
             */
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
//            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue("HelloWorld1");

            messageConsumer = session.createConsumer(destination);

            session.commit();
            // 接收消息
            while (true) {
                // 10000 设置毫秒数 使用receive进行接收属于同步接收
                TextMessage textMessage = (TextMessage)messageConsumer.receive(10000);

                if(null != textMessage ) {
                    System.out.println("get()=ActiveMQ:" + textMessage.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
