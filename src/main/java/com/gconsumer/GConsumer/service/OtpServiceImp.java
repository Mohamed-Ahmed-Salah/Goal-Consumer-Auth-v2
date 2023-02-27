package com.gconsumer.GConsumer.service;

import com.gconsumer.GConsumer.config.Constant;
import com.gconsumer.GConsumer.dto.request.*;
import com.gconsumer.GConsumer.dto.response.*;
import com.gconsumer.GConsumer.model.Otp;
import com.gconsumer.GConsumer.repository.OtpRepo;
import com.gconsumer.GConsumer.repository.UserCredentialRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OtpServiceImp implements OtpService {

    private final OtpRepo otpRepo;
    private final UserCredentialRepo userCredentialRepo;

   // @SneakyThrows
    @Override
    public GeneralResponse save(OtpRequest otpRequest, String createdBy) {
        if (userCredentialRepo.findById(otpRequest.getUserId()).isPresent()) {
            Otp otp = otpRequest.toOtp();
            otp.setCreatedBy(createdBy);
            otpRepo.save(otp);
            return  new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg,  null);
        }
        return  new GeneralResponse(Constant.ResponseCode.OtpNotFound.code, Constant.ResponseCode.OtpNotFound.msg,  null);
    }

    @Override
    public GeneralResponse delete(Long id) {
        if (otpRepo.findById(id).isPresent()) {
            Otp otp = otpRepo.findById(id).get();
            otpRepo.delete(otp);
            return  new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg,  null);
        }
        return  new GeneralResponse(Constant.ResponseCode.OtpNotFound.code, Constant.ResponseCode.OtpNotFound.msg,  null);
    }

    @Override
    public GeneralResponse update(Long id, OtpRequest otpRequest, String modifiedBy) {
        Optional<Otp> otpOptional = otpRepo.findById(id);
        if (otpOptional.isPresent()) {
            Otp otp = otpOptional.get();
            otp.setStatus(otpRequest.getStatus()).setModifiedBy(modifiedBy);
            otp.setStatus(otpRequest.getStatus());
            return  new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg,  null);
        }
        return  new GeneralResponse(Constant.ResponseCode.OtpNotFound.code, Constant.ResponseCode.OtpNotFound.msg,  null);
    }

    @Override
    public GeneralResponse getOTPs() {
        return null;
    }

    @Override
    public GeneralResponse getOtp(Long id) {
        return null;
    }


}
