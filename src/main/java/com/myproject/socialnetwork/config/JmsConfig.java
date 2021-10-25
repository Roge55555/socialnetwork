package com.myproject.socialnetwork.config;

import lombok.extern.log4j.Log4j2;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
@Log4j2
public class JmsConfig {
    private static final String JMS_BROKER_URL = "vm://embedded?broker.persistent=false,useShutdownHook=false";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(JMS_BROKER_URL);
    }

}
