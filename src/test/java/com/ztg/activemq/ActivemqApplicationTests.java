package com.ztg.activemq;

import com.ztg.activemq.jms.JMSConsumer;
import com.ztg.activemq.jms.JMSProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivemqApplicationTests {

	@Test
	public void contextLoads() {
	}

	/**
	 * ActiveMQ的点对点发送消息
	 */
	@Test
	public void activeMQSendTest() {
		JMSProducer jmsProducer = new JMSProducer();
		jmsProducer.start();
	}

	/**
	 * ActiveMQ的点对点接收消息
	 */
	@Test
	public void activeMQReceive() {
		JMSConsumer jmsConsumer = new JMSConsumer();
		jmsConsumer.init();
	}
}
