package com.gconsumer.GConsumer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class OtpVerifyRequest {

    @JsonProperty("otp")
    @NotBlank(message = "otp is mandatory")
    private String otp;

    @JsonProperty("user_id")
    @NotNull(message = "user id is mandatory")
    private Long userId;
}
