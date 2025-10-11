package com.microshop.orderservice.configuration;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class AxonConfig {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * ✅ Gắn TransactionManager cho Axon
     */
    @Bean
    public TransactionManager axonTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return new SpringTransactionManager(platformTransactionManager);
    }

    /**
     * ✅ StorageEngine có TransactionManager (Axon yêu cầu bắt buộc)
     */
    @Bean
    public EventStorageEngine storageEngine(TransactionManager axonTransactionManager) {
        return JpaEventStorageEngine.builder()
                .entityManagerProvider(() -> entityManager)
                .transactionManager(axonTransactionManager)
                .build();
    }

    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine) {
        return EmbeddedEventStore.builder().storageEngine(storageEngine).build();
    }

    @Bean
    public SimpleCommandBus commandBus() {
        SimpleCommandBus bus = SimpleCommandBus.builder().build();
        bus.registerHandlerInterceptor(new BeanValidationInterceptor<>());
        return bus;
    }

    @Bean
    public CommandGateway commandGateway(SimpleCommandBus bus) {
        return DefaultCommandGateway.builder()
                .commandBus(bus)
                .build();
    }
}
