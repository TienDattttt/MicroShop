package com.microshop.productservice.repository;

import com.microshop.productservice.entity.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearchDocument, String> { }

