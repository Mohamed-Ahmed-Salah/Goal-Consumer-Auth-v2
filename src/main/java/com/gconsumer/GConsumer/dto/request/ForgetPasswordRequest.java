package com.gconsumer.GConsumer.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class ForgetPasswordRequest {

    @NotBlank(message = "Email is mandatory")
    private String email;
}
