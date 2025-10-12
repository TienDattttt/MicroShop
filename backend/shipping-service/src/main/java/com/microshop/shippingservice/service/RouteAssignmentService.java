package com.microshop.shippingservice.service;

import org.springframework.stereotype.Service;

@Service
public class RouteAssignmentService {
    public String assignShipperByDistrict(String district) {
        if (district == null) return "SHIPPER-DEFAULT";
        String key = district.trim().toLowerCase();
        if (key.contains("1")) return "SHIPPER-D1";
        if (key.contains("3")) return "SHIPPER-D3";
        if (key.contains("thủ đức") || key.contains("thu duc")) return "SHIPPER-TD";
        return "SHIPPER-DEFAULT";
    }
}
