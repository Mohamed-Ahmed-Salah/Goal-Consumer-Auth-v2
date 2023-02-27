package com.gconsumer.GConsumer.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class RegistrationRequest {
//    @NotBlank(message = "username field is mandatory")
//    private String username;

//    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Invalid password")
//    private String password;


    @JsonProperty("full_name")
    @NotBlank(message = "full_name field is mandatory")
    private String fullName;

    @JsonProperty("company")
    @NotBlank(message = "company field is mandatory")
    private String company;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Invalid phone")
    private String phone;

    @JsonProperty("app_name")
    private String applicationName;
    @JsonProperty("app_description")
    private String applicationDescription;


    @NotBlank(message = "Invalid password")
    private String password;


    @JsonProperty("password_confirmation")
    @NotBlank(message = "Invalid password")
    private String passwordConfirmation;


    @JsonProperty("notes")
    private String notes;

    private String role="USER";


//    public UserCredential toUserAuthData() {
//        System.out.println("Saving");
//        return new UserCredential(this.getFullName(), null, this.getEmail(), this.getPhone(), true, USER_STATUS.PENDING.name(), true, true,this.getRole(), this.getNotes());
//    }

//    public UserPreference toUserProfile() {
//        return new UserPreference(null, null, null,  new UserCredential());
//    }
}
