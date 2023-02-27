package com.gconsumer.GConsumer.security;

import com.gconsumer.GConsumer.authService.UserDetailsServiceImpl;
import com.gconsumer.GConsumer.config.CustomAuthorizationFilter;
import com.gconsumer.GConsumer.exception.AuthorizationDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder encoder;

    String[] PUBLIC_URL = {
            "/auth/login",
            "/auth/registration",
            "/auth/forget-password",
            "/swagger-ui/**",
            "/v3/**"
    };

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthorizationDeniedHandler authorizationDeniedHandler(){
        return new AuthorizationDeniedHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Component("userSecurity")
    public class UserSecurity {
        public boolean hasUserId(Authentication authentication, String userId) {
            return authentication.getPrincipal().equals(userId);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
//                .antMatchers("/auth/user/{username}").access("@userSecurity.hasUserId(authentication,#username)")
                .antMatchers(PUBLIC_URL)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(authorizationDeniedHandler())


        ;
/*
                .and()
                .requiresChannel()
                .antMatchers("/**")
                .requiresSecure();
*/


    }

/*
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("OPTIONS", "POST", "PUT", "GET", "DELETE", "ORIGIN"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
*/


}
