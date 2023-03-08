package com.gconsumer.GConsumer.dto;

import com.gconsumer.GConsumer.dto.response.UserDataResponse;
import com.gconsumer.GConsumer.model.UserCredential;

public class UserMapper {

    public UserDataResponse mapFromUserToLoginResponse(UserCredential userCredential){

        return new UserDataResponse(userCredential.getId(), userCredential.getEmail(), userCredential.getFullName(), userCredential.getPhone(),userCredential.isEnable(),userCredential.getStatus(),userCredential.isLock() ,userCredential.getRole());
    }




}
