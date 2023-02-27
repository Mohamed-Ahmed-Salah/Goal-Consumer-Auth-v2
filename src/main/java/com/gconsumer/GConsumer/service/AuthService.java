package com.gconsumer.GConsumer.service;

import com.gconsumer.GConsumer.dto.request.*;
import com.gconsumer.GConsumer.dto.response.*;

import java.text.ParseException;

public interface AuthService {

    GeneralResponse login(LoginRequest loginRequest) throws ParseException;

    GeneralResponse registration(RegistrationRequest registrationRequest) throws ParseException;

    GeneralResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest);

    GeneralResponse resetPassword(Long id, ResetPasswordRequest resetPasswordRequest);

    GeneralResponse verifyOtp(OtpVerifyRequest otpVerifyRequest) throws ParseException;

//    GeneralResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest);

    public GeneralResponse handleBadCredential(LoginRequest loginRequest);

    GeneralResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest);

    GeneralResponse lock(Long id);

    GeneralResponse unlock(Long id);

    void logout();

    boolean checkLastTwo(Long userId);

//    void sendEmail() throws AddressException;
}
