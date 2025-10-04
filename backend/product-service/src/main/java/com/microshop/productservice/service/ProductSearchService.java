package com.microshop.productservice.service;

import com.microshop.productservice.dto.request.ProductSearchRequest;
import com.microshop.productservice.entity.ProductSearchDocument;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchOperations operations;

    public Page<ProductSearchDocument> search(ProductSearchRequest req) {

        var boolQuery = QueryBuilders.bool();

        // full-text theo name/description
        if (req.getQ() != null && !req.getQ().isBlank()) {
            boolQuery.must(QueryBuilders.multiMatch()
                    .fields("name^2", "description")
                    .query(req.getQ())
                    .build()._toQuery());
        }

        // brand filter
        if (req.getBrand() != null && !req.getBrand().isBlank()) {
            boolQuery.filter(QueryBuilders.term(t -> t.field("brand").value(req.getBrand())));
        }

        // category filter
        if (req.getCategoryId() != null && !req.getCategoryId().isBlank()) {
            boolQuery.filter(QueryBuilders.term(t -> t.field("category_id").value(req.getCategoryId())));
        }

        // price range
        if (req.getMinPrice() != null || req.getMaxPrice() != null) {
            boolQuery.filter(QueryBuilders.range(r -> {
                r.field("price");
                if (req.getMinPrice() != null)
                    r.gte(JsonData.of(req.getMinPrice()));
                if (req.getMaxPrice() != null)
                    r.lte(JsonData.of(req.getMaxPrice()));
                return r;
            }));
        }

        // rating filter
        if (req.getMinRating() != null) {
            boolQuery.filter(QueryBuilders.range(r ->
                    r.field("rating").gte(JsonData.of(req.getMinRating()))));
        }

        // chỉ hiển thị sản phẩm enable
        boolQuery.filter(QueryBuilders.term(t -> t.field("enabled").value(true)));

        // paging + sort
        Sort sort = Sort.unsorted();
        if (req.getSort() != null && req.getSort().contains(",")) {
            String[] parts = req.getSort().split(",");
            sort = Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
        }
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);

        // build query
        NativeQuery query = NativeQuery.builder()
                .withQuery(boolQuery.build()._toQuery())
                .withPageable(pageable)
                .build();

        // execute
        SearchHits<ProductSearchDocument> hits = operations.search(query, ProductSearchDocument.class);
        List<ProductSearchDocument> content = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(content, pageable, hits.getTotalHits());
    }
}
