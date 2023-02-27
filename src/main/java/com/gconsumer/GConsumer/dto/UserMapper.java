package com.gconsumer.GConsumer.dto;

import com.gconsumer.GConsumer.dto.request.RegistrationRequest;
import com.gconsumer.GConsumer.dto.response.UserDataResponse;
import com.gconsumer.GConsumer.model.UserCredential;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void updateUserFromDto(UserUpdateRequest userUpdateRequest, @MappingTarget UserCredential userCredential);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapFromRegisterToUser(RegistrationRequest registrationRequest, @MappingTarget UserCredential userCredential);

    void mapFromUserToLogin(UserCredential userCredential, @MappingTarget UserDataResponse userDataResponse);




}
