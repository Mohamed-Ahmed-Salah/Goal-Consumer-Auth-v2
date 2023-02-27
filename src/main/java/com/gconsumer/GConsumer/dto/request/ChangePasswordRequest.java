package com.gconsumer.GConsumer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class ChangePasswordRequest {

    @JsonProperty("new_password")
    @NotBlank(message = "new_password is mandatory")
    private String newPassword;

    @JsonProperty("old_password")
    @NotBlank(message = "old_password is mandatory")
    private String oldPassword;

    @JsonProperty("old_password_confirm")
    @NotBlank(message = "old_password_confirm is mandatory")
    private String oldPasswordConfirmation;
}
