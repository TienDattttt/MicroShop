package com.microshop.paymentservice.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {
    @Bean
    public io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer defaultCb() {
        return (name, builder) -> builder
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10));
    }
}

