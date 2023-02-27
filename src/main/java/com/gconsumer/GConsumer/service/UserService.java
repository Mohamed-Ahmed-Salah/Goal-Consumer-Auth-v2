package com.gconsumer.GConsumer.service;

import com.gconsumer.GConsumer.dto.response.GeneralResponse;
import com.gconsumer.GConsumer.model.UserCredential;


public interface UserService {

    GeneralResponse delete(Long id);

//    GeneralResponse update(Long id, UserUpdateRequest userUpdateRequest);

    GeneralResponse getUser(String username);

    GeneralResponse getUsers();

    UserCredential findById(Long id);
}
