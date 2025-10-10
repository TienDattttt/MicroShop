package com.microshop.paymentservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean NewTopic paymentTopic(){ return new NewTopic("payment.events", 3, (short)1); }
}

