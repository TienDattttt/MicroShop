package com.microshop.identityservice.controller;


import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class JwksController {
    private final JWKSet jwkSet;

    @GetMapping("/jwks.json")
    public Map<String, Object> jwks() {
        return jwkSet.toJSONObject();
    }
}

