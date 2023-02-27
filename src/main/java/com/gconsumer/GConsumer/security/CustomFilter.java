package com.gconsumer.GConsumer.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gconsumer.GConsumer.model.UserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManagerBean;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            System.out.println("request.getParameter(username)");

            System.out.println(request.getParameter("username"));
            return authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserCredential userCredential = (UserCredential) authResult.getPrincipal();
        Algorithm alg = Algorithm.HMAC256("${security.token.algorithm-key}".getBytes());
        String accessToken = JWT.create()
                .withSubject(userCredential.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
//                .withClaim("email", userCredential.getEmail())
                .withClaim("active", userCredential.isEnable())
                .withClaim("email", userCredential.getEmail())
                .withClaim("roles", authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(alg);
        String refreshToken = JWT.create()
                .withSubject(userCredential.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(alg);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
