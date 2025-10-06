package com.microshop.identityservice.controller;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/.well-known")
@RequiredArgsConstructor
public class JwksController {

    private final JWKSet jwkSet;

    @GetMapping("/jwks.json")
    public Map<String, Object> jwks() {
        List<JWK> enhancedKeys = jwkSet.getKeys().stream()
                .map(jwk -> {
                    if (jwk instanceof RSAKey rsaKey) {
                        try {
                            // Tạo key mới có alg và use đầy đủ
                            return new RSAKey.Builder(rsaKey.toRSAPublicKey())
                                    .keyID(rsaKey.getKeyID())
                                    .algorithm(new Algorithm("RS256"))
                                    .keyUse(KeyUse.SIGNATURE)
                                    .build();
                        } catch (JOSEException e) {
                            // Nếu có lỗi khi convert public key
                            throw new RuntimeException("Error building RSA JWK", e);
                        }
                    }
                    return jwk;
                })
                .collect(Collectors.toList());

        return new JWKSet(enhancedKeys).toJSONObject();
    }
}
