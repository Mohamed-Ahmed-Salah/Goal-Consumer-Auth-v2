package com.gconsumer.GConsumer.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDataResponse {

    private Long id;

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private int version;

    private String username;

    private String email;

    private String country;

    private String city;

    private String fullName;

    private String phone;

    private boolean enable;

    private String status;

    private boolean lock;

    private boolean otp;

    private String role;

    private String notes;
}
