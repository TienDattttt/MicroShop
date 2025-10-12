package com.microshop.shippingservice.controller;

import com.microshop.shippingservice.dto.request.FailRequest;
import com.microshop.shippingservice.dto.request.PickupRequest;
import com.microshop.shippingservice.dto.response.ApiResponse;
import com.microshop.shippingservice.dto.response.ShipmentResponse;
import com.microshop.shippingservice.service.ShippingProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentCommandController {

    private final ShippingProcessorService service;

    @PostMapping("/{id}/pickup")
    public ApiResponse<ShipmentResponse> pickup(@PathVariable("id") String id,
                                                @RequestBody(required = false) PickupRequest req) {
        var res = service.markPickedUp(id, req != null ? req.getPickupTime() : null);
        return ApiResponse.ok(res);
    }

    @PostMapping(value = "/{id}/deliver", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ShipmentResponse> deliver(@PathVariable("id") String id,
                                                 @RequestParam(value = "deliveredTime", required = false) String deliveredTime,
                                                 @RequestPart(value = "photos", required = false) MultipartFile[] photos) {
        List<String> urls = new ArrayList<>();
        if (photos != null) {
            for (MultipartFile f : photos) {
                urls.add("/uploads/" + f.getOriginalFilename());
            }
        }
        var res = service.markDelivered(id,
                deliveredTime != null ? Instant.parse(deliveredTime) : null,
                urls);
        return ApiResponse.ok(res);
    }

    @PostMapping("/{id}/fail")
    public ApiResponse<ShipmentResponse> fail(@PathVariable("id") String id,
                                              @RequestBody @Validated FailRequest req) {
        var res = service.markFailed(id, req.getFailedTime(), req.getReason());
        return ApiResponse.ok(res);
    }
}
