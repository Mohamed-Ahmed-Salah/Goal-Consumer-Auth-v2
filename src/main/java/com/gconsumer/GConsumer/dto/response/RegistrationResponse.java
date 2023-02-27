package com.gconsumer.GConsumer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@AllArgsConstructor
public class RegistrationResponse extends GenericResponse {

//
//
//    public RegistrationResponse fromUser(UserCredential userCredential) {
//        System.out.println("From User To Response: " + userCredential);
//        return new RegistrationResponse(userCredential.getId(), userCredential.getUsername(), userCredential.getEmail(), userCredential.getFirstName(),userCredential.getLastName(), userCredential.getPhone(), userCredential.isEnable(), userCredential.isLock(), userCredential.getAccessMethod(),userCredential.getAuthMethod(),userCredential.getNotes());
//    }
}
