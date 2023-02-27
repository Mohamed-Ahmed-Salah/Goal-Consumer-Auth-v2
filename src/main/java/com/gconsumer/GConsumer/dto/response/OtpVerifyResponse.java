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
public class OtpVerifyResponse extends GenericResponse{
    private String message;
}
