package com.microshop.identityservice.controller;


import com.microshop.identityservice.dto.request.AuthenticationRequest;
import com.microshop.identityservice.dto.request.LogoutRequest;
import com.microshop.identityservice.dto.request.RefreshRequest;
import com.microshop.identityservice.dto.response.ApiResponse;
import com.microshop.identityservice.dto.response.AuthenticationResponse;
import com.microshop.identityservice.dto.response.IntrospectResponse;
import com.microshop.identityservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> token(@Valid @RequestBody AuthenticationRequest req){
        return ApiResponse.ok(authService.issueToken(req));
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody String token){
        // token body có thể là {"token":"..."} – em có thể chỉnh parser nếu muốn
        String t = token.replace("{","").replace("}","").replace("\"","").replace("token:","").trim();
        return ApiResponse.ok(authService.introspect(t));
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@Valid @RequestBody RefreshRequest req){
        return ApiResponse.ok(authService.refresh(req));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Valid @RequestBody LogoutRequest req){
        authService.logout(req);
        return ApiResponse.ok();
    }
}