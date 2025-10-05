package com.microshop.productservice.service;

import com.microshop.productservice.dto.request.ProductCreateRequest;
import com.microshop.productservice.dto.request.ProductUpdateRequest;
import com.microshop.productservice.dto.request.ProductSearchRequest;
import com.microshop.productservice.dto.response.ProductResponse;
import com.microshop.productservice.entity.Product;
import com.microshop.productservice.entity.ProductSearchDocument;
import com.microshop.productservice.exception.AppException;
import com.microshop.productservice.exception.ErrorCode;
import com.microshop.productservice.mapper.ProductMapper;
import com.microshop.productservice.repository.ProductRepository;
import com.microshop.productservice.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final ProductSearchRepository searchRepo;
    private final ProductSearchService searchService;
    private final ProductMapper mapper;

    /* CRUD Mongo + đồng bộ chỉ mục ES */

    public ProductResponse create(ProductCreateRequest req){
        if (productRepo.existsBySku(req.getSku())) throw new AppException(ErrorCode.PRODUCT_DUPLICATED_SKU);
        Product entity = mapper.toEntity(req);
        entity.setRating(0.0);
        entity.setReviewCount(0);
        Product saved = productRepo.save(entity);
        upsertIndex(saved);
        return mapper.toResponse(saved);
    }

    public ProductResponse get(String id){
        return productRepo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public Page<ProductResponse> list(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return productRepo.findAll(pageable).map(mapper::toResponse);
    }

    public ProductResponse update(String id, ProductUpdateRequest req){
        Product p = productRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        mapper.updateEntityFromDto(req, p);
        Product saved = productRepo.save(p);
        upsertIndex(saved);
        return mapper.toResponse(saved);
    }

    public void delete(String id){
        if (!productRepo.existsById(id)) throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        productRepo.deleteById(id);
        searchRepo.deleteById(id);
    }

    /* Search (Elasticsearch) */
    public Page<ProductSearchDocument> search(ProductSearchRequest req){
        return searchService.search(req);
    }

    /* helper: sync index */
    private void upsertIndex(Product p){
        searchRepo.save(mapper.toSearch(p));
    }
}

