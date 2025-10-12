package com.microshop.shippingservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND("SHIPPING_NOT_FOUND"),
    INVALID_STATE("SHIPPING_INVALID_STATE"),
    BAD_REQUEST("BAD_REQUEST"),
    INTERNAL_ERROR("INTERNAL_ERROR");

    private final String code;
}
