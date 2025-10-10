package com.microshop.paymentservice.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean public Logger.Level feignLoggerLevel(){ return Logger.Level.BASIC; }
}

