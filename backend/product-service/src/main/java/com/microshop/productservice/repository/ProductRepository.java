package com.microshop.productservice.repository;


import com.microshop.productservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsBySku(String sku);
}
