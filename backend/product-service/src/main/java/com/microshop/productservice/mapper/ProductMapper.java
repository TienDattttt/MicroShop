package com.microshop.productservice.mapper;

import com.microshop.productservice.dto.request.ProductCreateRequest;
import com.microshop.productservice.dto.request.ProductUpdateRequest;
import com.microshop.productservice.dto.response.ProductResponse;
import com.microshop.productservice.entity.Product;
import com.microshop.productservice.entity.ProductSearchDocument;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductCreateRequest req);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductUpdateRequest req, @MappingTarget Product entity);

    ProductResponse toResponse(Product p);

    @Mapping(target = "id", source = "id")
    ProductSearchDocument toSearch(Product p);
}

