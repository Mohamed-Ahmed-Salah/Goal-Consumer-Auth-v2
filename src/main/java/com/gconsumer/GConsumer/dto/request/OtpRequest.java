package com.gconsumer.GConsumer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gconsumer.GConsumer.model.Otp;
import com.gconsumer.GConsumer.model.UserCredential;
import com.gconsumer.GConsumer.service.UserServiceImp;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.gconsumer.GConsumer.utitlty.Utility.getOTP;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class OtpRequest {
    private final UserServiceImp userServiceImp;

    @JsonProperty("user_id")
    @NotBlank(message = "user_id is mandatory")
    private Long userId;

    @JsonProperty("status")
    @NotBlank(message = "status is mandatory")
    private String status;

    public Otp toOtp() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-mm-dd HH:mm:ss");
        String expirationDate = LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES)).format(dateTimeFormatter);
        UserCredential userCredential = userServiceImp.findById(this.userId);
        String otp = getOTP(4);
        return new Otp(userCredential, otp, expirationDate, null);
    }
}
