package com.gconsumer.GConsumer.service;

import com.gconsumer.GConsumer.config.Constant;
import com.gconsumer.GConsumer.dto.UserMapper;
import com.gconsumer.GConsumer.dto.response.GeneralResponse;
import com.gconsumer.GConsumer.dto.response.UserDataResponse;
import com.gconsumer.GConsumer.enums.USER_STATUS;
import com.gconsumer.GConsumer.filter.SearchRequest;
import com.gconsumer.GConsumer.filter.SearchSpecification;
import com.gconsumer.GConsumer.model.UserCredential;
import com.gconsumer.GConsumer.repository.UserCredentialRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImp implements UserService {

    private final UserCredentialRepo userCredentialRepo;
    private final UserMapper userMapper;

    @Override
    public GeneralResponse delete(Long id) {
        Optional<UserCredential> user = userCredentialRepo.findById(id);
        if (user.isPresent()) {
            UserCredential userCredential = user.get();
            userCredential.setStatus(USER_STATUS.CLOSED.name());
            return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, null);
        }
        return new GeneralResponse(Constant.ResponseCode.UserNotFound.code, Constant.ResponseCode.UserNotFound.msg,
                null);
    }

    @Override
    public GeneralResponse getUser(String username) {
        return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg,
                userCredentialRepo.findByUsername(username));
}

    public GeneralResponse getUserById(Long userId){
        Optional<UserCredential> optionalUserCredential = userCredentialRepo.findById(userId);
        return optionalUserCredential.map(userCredential -> new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, userCredential)).orElseGet(() -> new GeneralResponse(Constant.ResponseCode.UserNotFound.code, Constant.ResponseCode.UserNotFound.msg, null));
    }

//    public GeneralResponse getUsersByFilter(FilterRequest filterRequest){
//       return new GeneralResponse(Constant.ResponseCode.Success.code,Constant.ResponseCode.Success.msg,userCredentialRepo.findByFilter(filterRequest.getFirstName(), filterRequest.getLastName()));
//
//    }

    @Override
    public GeneralResponse getUsers() {
        return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg,
                userCredentialRepo.findAll());
    }

    public GeneralResponse search(SearchRequest request){
        List<UserDataResponse> userDataResponses = new ArrayList<>();
//        UserDataResponse userDataResponse = new UserDataResponse();
        SearchSpecification<UserCredential> specification = new SearchSpecification<>(request);
//        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        userCredentialRepo.findAll(specification).forEach(userCredential -> {
           UserDataResponse userDataResponse= userMapper.mapFromUserToLoginResponse(userCredential);
            userDataResponses.add(userDataResponse);
        });
        return new GeneralResponse(Constant.ResponseCode.Success.code,Constant.ResponseCode.Success.msg,userDataResponses);
    }

    @Override
    public UserCredential findById(Long id) {
        return userCredentialRepo.findById(id).get();
    }
}
