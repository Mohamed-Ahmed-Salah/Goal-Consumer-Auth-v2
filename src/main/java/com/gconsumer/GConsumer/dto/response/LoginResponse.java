package com.gconsumer.GConsumer.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Data
@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
public class LoginResponse extends GenericResponse {

    private String accessToken;
    private String message;
    private String refreshToken;
}
