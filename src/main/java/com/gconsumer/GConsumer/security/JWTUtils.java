package com.gconsumer.GConsumer.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gconsumer.GConsumer.dto.request.LoginRequest;
import com.gconsumer.GConsumer.model.Permission;
import com.gconsumer.GConsumer.repository.PermissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTUtils {
    @Value("${security.token.access.time-millis}")
    private Long ACCESS_TOKEN_MILLIS;
    @Value("${security.token.refresh.time-millis}")
    private Long REFRESH_TOKEN_MILLIS;
    @Value("${security.token.algorithm-key}")
    private String ALG_KEY;
    private final PermissionRepo permissionRepo;

//    public JWTUtils(@Value("${security.token.access.time-millis}") Long access, @Value("${security.token.refresh.time-millis}") Long refresh, @Value("${security.token.algorithm-key}") String key, PermissionRepo ) {
//        this.ACCESS_TOKEN_MILLIS = access;
//        this.REFRESH_TOKEN_MILLIS = refresh;
//        this.ALG_KEY = key;
//    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(ALG_KEY.getBytes());
    }

//    public static Optional<UsernamePasswordAuthenticationToken> getAuthenticationToken(String token){
//        return getAuthenticationToken(token).get().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//    }
//    public static Optional<UsernamePasswordAuthenticationToken> getAuthentication(String token){
//        return decodeToken()
//                decodeToken(token)
//                .map(decodedJWT -> {
//                    if(decodedJWT != null && decodedJWT.getIssuer().equals("error"))
//                        return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), decodedJWT.getIssuer(), null);
//                    String username = decodedJWT.getSubject();
//                    List<GrantedAuthority> authorities = Stream.of(decodedJWT.getClaim("roles").asArray(String.class))
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());
//                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
//        });
//    }

    public String getAccessToken(LoginRequest loginRequest, String issuer, List<String> roles) {
        List<Permission> permissions = permissionRepo.findByUsername(loginRequest.getUsername());
        List<String> operations = new ArrayList<>();
        permissions.forEach(permission -> {
            operations.add(permission.getOperation().getName());
        });
//        optionalPermission.ifPresent(permission -> operations.add(permission.getOperation().getName()));

        return JWT.create()
                .withSubject(loginRequest.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_MILLIS))
                .withIssuer(issuer)
                .withClaim("name", loginRequest.getUsername())
                .withClaim("roles", roles)
                .withClaim("operations",operations)
                .sign(getAlgorithm());
    }

    public String getRefreshToken(LoginRequest loginRequest, String issuer, List<String> roles) {
        return JWT.create()
                .withSubject(loginRequest.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_MILLIS))
                .withIssuer(issuer)
                .withClaim("roles", roles)
                .sign(getAlgorithm());
    }
//
//    public Optional<Map<String, Object>> getTokens(Optional<AccountDto> accountDtoMono, String issuer, List<String> roles){
//        return accountDtoMono.flatMap(accountDto -> {
//            String access = getAccessToken(accountDto, issuer, roles);
//            String refresh = getRefreshToken(accountDto.getUsername(), issuer, roles);
//            Map<String, Object> tokens = new HashMap<>();
//            tokens.put("accessToken", access);
//            tokens.put("refreshToken", refresh);
//            return Optional.of(tokens);
//        });
//    }

    public DecodedJWT decodeToken(String token) {
        return JWT.require(getAlgorithm()).build().verify(token);
    }
}
