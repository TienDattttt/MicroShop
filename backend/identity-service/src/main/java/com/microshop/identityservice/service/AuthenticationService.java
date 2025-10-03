package com.microshop.identityservice.service;

import com.microshop.identityservice.dto.request.AuthenticationRequest;
import com.microshop.identityservice.dto.request.LogoutRequest;
import com.microshop.identityservice.dto.request.RefreshRequest;
import com.microshop.identityservice.dto.response.AuthenticationResponse;
import com.microshop.identityservice.dto.response.IntrospectResponse;
import com.microshop.identityservice.entity.InvalidatedToken;
import com.microshop.identityservice.entity.Permission;
import com.microshop.identityservice.entity.Role;
import com.microshop.identityservice.entity.User;
import com.microshop.identityservice.exception.AppException;
import com.microshop.identityservice.exception.ErrorCode;
import com.microshop.identityservice.repository.InvalidatedTokenRepository;
import com.microshop.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepo;
    private final InvalidatedTokenRepository invalidRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    private static final long ACCESS_SECONDS = 3600;     // 1h
    private static final long REFRESH_SECONDS = 36000;   // 10h

    public AuthenticationResponse issueToken(AuthenticationRequest req){
        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (Boolean.FALSE.equals(user.getEnabled())) throw new AppException(ErrorCode.USER_DISABLED);
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash()))
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED);

        String scope = buildScope(user);
        String access = generateJwt(user.getId().toString(), scope, ACCESS_SECONDS, "access");
        String refresh = generateJwt(user.getId().toString(), scope, REFRESH_SECONDS, "refresh");

        return AuthenticationResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .expiresIn(ACCESS_SECONDS)
                .tokenType("Bearer")
                .build();
    }

    public IntrospectResponse introspect(String token){
        try {
            Jwt jwt = jwtDecoder.decode(token);
            boolean active = !isInvalidated(jwt) && jwt.getExpiresAt() != null && jwt.getExpiresAt().isAfter(Instant.now());
            String scope = Optional.ofNullable(jwt.getClaimAsString("scope")).orElse("");
            return IntrospectResponse.builder()
                    .active(active)
                    .subject(jwt.getSubject())
                    .scope(scope)
                    .exp(jwt.getExpiresAt() == null ? null : jwt.getExpiresAt().getEpochSecond())
                    .build();
        } catch (JwtException e){
            return IntrospectResponse.builder().active(false).build();
        }
    }

    public AuthenticationResponse refresh(RefreshRequest req){
        Jwt jwt = parseAndValidate(req.getRefreshToken());
        if (!"refresh".equals(jwt.getClaimAsString("typ"))) throw new AppException(ErrorCode.UNAUTHORIZED);
        String sub = jwt.getSubject();
        User user = userRepo.findById(UUID.fromString(sub)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String scope = buildScope(user);
        String access = generateJwt(sub, scope, ACCESS_SECONDS, "access");
        String refresh = generateJwt(sub, scope, REFRESH_SECONDS, "refresh");
        return AuthenticationResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .expiresIn(ACCESS_SECONDS)
                .tokenType("Bearer")
                .build();
    }

    public void logout(LogoutRequest req){
        Jwt jwt = parseAndValidate(req.getToken());
        String jti = jwt.getId();
        Instant exp = jwt.getExpiresAt();
        if (jti != null && exp != null){
            invalidRepo.save(InvalidatedToken.builder().id(jti).expiryTime(exp).build());
        }
    }

    /* ----------------- helpers ----------------- */

    private String generateJwt(String subject, String scope, long seconds, String typ){
        Instant now = Instant.now();
        String jti = UUID.randomUUID().toString();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("microshop-identity")
                .issuedAt(now)
                .expiresAt(now.plus(seconds, ChronoUnit.SECONDS))
                .subject(subject)
                .id(jti)
                .claim("typ", typ)
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private Jwt parseAndValidate(String token){
        try {
            Jwt jwt = jwtDecoder.decode(token);
            if (isInvalidated(jwt)) throw new AppException(ErrorCode.UNAUTHORIZED);
            return jwt;
        } catch (JwtException e){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    private boolean isInvalidated(Jwt jwt){
        String jti = jwt.getId();
        return jti != null && invalidRepo.findById(jti).isPresent();
    }

    private String buildScope(User user){
        Set<String> perms = user.getRoles().stream()
                .filter(Objects::nonNull)
                .map(Role::getPermissions)
                .filter(Objects::nonNull)
                .flatMap(Set::stream)
                .map(Permission::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        // role cũng có thể thêm vào scope dạng role:ADMIN
        user.getRoles().forEach(r -> perms.add("role:" + r.getName()));
        return String.join(" ", perms);
    }
}

