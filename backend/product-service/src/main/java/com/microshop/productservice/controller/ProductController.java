package com.microshop.productservice.controller;

import com.microshop.productservice.dto.request.*;
import com.microshop.productservice.dto.response.ApiResponse;
import com.microshop.productservice.dto.response.ProductResponse;
import com.microshop.productservice.entity.ProductSearchDocument;
import com.microshop.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    /* ------ Public (GET) ------ */

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> get(@PathVariable String id){
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping
    public ApiResponse<Page<ProductResponse>> list(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "12") int size){
        return ApiResponse.ok(service.list(page, size));
    }

    @GetMapping("/search")
    public ApiResponse<Page<ProductSearchDocument>> search(@Valid ProductSearchRequest req){
        return ApiResponse.ok(service.search(req));
    }

    /* ------ Protected (WRITE) ------ */

    @PostMapping
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductCreateRequest req){
        return ApiResponse.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable String id, @RequestBody ProductUpdateRequest req){
        return ApiResponse.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id){
        service.delete(id);
        return ApiResponse.ok();
    }
}

