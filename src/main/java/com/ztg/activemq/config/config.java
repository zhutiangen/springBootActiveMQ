package com.ztg.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class config {

    /**
     * 配置Queue
     * @return
     */
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("demo.queue");
    }


}
