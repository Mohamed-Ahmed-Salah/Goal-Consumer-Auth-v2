package com.gconsumer.GConsumer.config;

import com.gconsumer.GConsumer.dto.UserMapper;
import com.gconsumer.GConsumer.dto.request.RegistrationRequest;
import com.gconsumer.GConsumer.dto.response.UserDataResponse;
import com.gconsumer.GConsumer.model.UserCredential;
import com.gconsumer.GConsumer.validatation.ErrorMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Beans {
    @Bean("account")
    public UserCredential account() {
        return new UserCredential();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ErrorMessage errorMessage() {
        return new ErrorMessage();
    }


    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }


}
