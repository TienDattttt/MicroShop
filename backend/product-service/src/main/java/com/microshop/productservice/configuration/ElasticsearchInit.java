package com.microshop.productservice.configuration;


import com.microshop.productservice.entity.ProductSearchDocument;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

@Configuration
@RequiredArgsConstructor
public class ElasticsearchInit {
    private static final Logger log = LoggerFactory.getLogger(ElasticsearchInit.class);

    private final ElasticsearchOperations operations;

    @Bean
    ApplicationRunner ensureIndex() {
        return args -> {
            IndexOperations idx = operations.indexOps(ProductSearchDocument.class);
            if (!idx.exists()) {
                log.info("Creating ES index 'products'...");
                idx.create();
                idx.putMapping(idx.createMapping(ProductSearchDocument.class));
            } else {
                log.info("ES index 'products' already exists");
            }
        };
    }
}

