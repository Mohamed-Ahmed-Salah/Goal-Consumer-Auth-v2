package com.gconsumer.GConsumer.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String[] arr = {"/auth/login", "/auth/registration","/auth/forget-password",
                "/swagger-ui/index.html", "/swagger-ui/swagger-ui.css",
                "/swagger-ui/index.css", "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/swagger-ui-standalone-preset.js",
                "/swagger-ui/swagger-initializer.js", "/swagger-ui/favicon-32x32.png",
                "/swagger-ui/favicon-16x16.png", "/v3/api-docs/swagger-config", "/v3/api-docs"};
//        if(request.getServletPath().equals("/auth/login") || request.getServletPath().equals("/auth/registration") || request.getServletPath().equals("/auth/forget-password") || request.getServletPath().equals("/swagger-ui/index.html") || request.getServletPath().equals("/v3/**")){
//            filterChain.doFilter(request, response);
//        }
        if (stream(arr).anyMatch(us -> us.equals(request.getServletPath()))) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationToken = request.getHeader(AUTHORIZATION);
            if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
                try {
                    String token = authorizationToken.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("sdknnsnsnn".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> tokens = new HashMap<>();
                    tokens.put("error_message", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                }
            } else {
                response.setStatus(FORBIDDEN.value());
                Map<String, String> tokens = new HashMap<>();
                tokens.put("error_message", "User not authenticated");
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
        }
    }
}
