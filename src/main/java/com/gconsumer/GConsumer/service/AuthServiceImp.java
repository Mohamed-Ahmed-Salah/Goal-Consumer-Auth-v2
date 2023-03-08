package com.gconsumer.GConsumer.service;

import com.gconsumer.GConsumer.config.Constant;
import com.gconsumer.GConsumer.dto.UserMapper;
import com.gconsumer.GConsumer.dto.request.*;
import com.gconsumer.GConsumer.dto.response.*;
import com.gconsumer.GConsumer.enums.LOGIN_STATUS;
import com.gconsumer.GConsumer.enums.USER_STATUS;
import com.gconsumer.GConsumer.model.EmailDetails;
import com.gconsumer.GConsumer.model.LoginHistory;
import com.gconsumer.GConsumer.model.Otp;
import com.gconsumer.GConsumer.model.UserCredential;
import com.gconsumer.GConsumer.repository.LoginHistoryRepo;
import com.gconsumer.GConsumer.repository.OtpRepo;
import com.gconsumer.GConsumer.repository.UserCredentialRepo;
import com.gconsumer.GConsumer.repository.UserPreferenceRepo;
import com.gconsumer.GConsumer.security.JWTUtils;
import com.gconsumer.GConsumer.validatation.ExistUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.gconsumer.GConsumer.utitlty.Utility.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImp implements AuthService {
    private final UserCredentialRepo userCredentialRepo;
    private final UserPreferenceRepo userPreferenceRepo;
    private final OtpRepo otpRepo;
    private final LoginHistoryRepo loginHistoryRepo;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

//    RegistrationResponse registrationResponse = new RegistrationResponse();
    Map<String, Object> data = new HashMap<>();


    @Override
    public GeneralResponse login(LoginRequest loginRequest) throws ParseException {
        Optional<UserCredential> userCredentialData = userCredentialRepo.findByEmailOrPhone(loginRequest.getUsername());
        LoginResponse loginResponse;
        System.out.println(userCredentialData.isPresent());
        try {
            if (userCredentialData.isPresent()) {

                System.out.println("HEREEEEEEEEEEEEEEEEEEEEEEEee"+userCredentialData.get().isLock());
//                if(userCredentialData.get().isLock()){
//                                return new GeneralResponse(Constant.ResponseCode.UserIsLocked.code, Constant.ResponseCode.UserIsLocked.msg, null);
//                }
                if (userCredentialData.get().isOtp()) {
                    loginResponse = otpLogin(loginRequest, userCredentialData);
                } else {
                    System.out.println("PASSSWORRRRRRRRRRRDDDDDDD");
                    loginResponse = passwordLogin(loginRequest, userCredentialData);
                }
                System.out.println("ALLGOOOOOOOOODDDD");

                return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, loginResponse);
            }
            return new GeneralResponse(Constant.ResponseCode.UserNotFound.code, Constant.ResponseCode.UserNotFound.msg, null);
        } catch (BadCredentialsException | ParseException | IllegalArgumentException ex) {
            loginResponse = (LoginResponse) handleBadCredential(loginRequest).getBody();
            // res.putAll(setResponseData(loginResponse.isStatus(), loginResponse.getData(), loginResponse.getMessage()));
            return new GeneralResponse(Constant.ResponseCode.WrongUsernameOrPassword.code, Constant.ResponseCode.WrongUsernameOrPassword.msg, null);
        }
    }

    @Override
    public GeneralResponse registration(RegistrationRequest registrationRequest) {
        ExistUser existUser = this.existUser(registrationRequest.getEmail(), registrationRequest.getPhone());
        if (existUser.isExist()) {
            System.out.println("IF");
            return new GeneralResponse(Constant.ResponseCode.Validation.code, Constant.ResponseCode.Validation.msg, null);
        }



//        System.out.println("ELSE");
//        log.info("Saving new user {} to the database", registrationRequest.getUsername());
//        System.out.println("Before saving");
//        UserCredential userCredential = new UserCredential();
//        userMapper.mapFromRegisterToUser(registrationRequest, userCredential);
//        userCredential.setOtp(true).setEnable(true).setLock(true);
//        System.out.println("After saving");
//        UserPreference userPreference = registrationRequest.toUserProfile();
//        userCredential.setCreatedBy("System");
//        userPreference.setCreatedBy("System");
//        userPreference.setUserCredential(userCredentialRepo.save(userCredential));
//        userPreferenceRepo.save(userPreference);
//        System.out.println("After saving user preference");
//        registrationResponse.setData(null);
//        sendOTP(userCredential);
        // return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, registrationResponse);

        if (!registrationRequest.getPassword().equals(registrationRequest.getPasswordConfirmation())) {

            return new GeneralResponse(Constant.ResponseCode.Validation.code, Constant.ResponseCode.Validation.msg, null);
        }
        System.out.println("correct passwordSSSSSSSSSSss");


        try {
            ModelMapper modelMapper = new ModelMapper();

            UserCredential userCredential = modelMapper.map(registrationRequest, UserCredential.class);
            userCredential.setOtp(false).setEnable(true).setLock(true);
            userCredential.setStatus(USER_STATUS.PENDING.name());


            userCredential.setPassword((passwordEncoder.encode(decodeToString(registrationRequest.getPassword()))));
            userCredentialRepo.save(userCredential);
            System.out.println("saved user");

//        sendOTP(userCredential);
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(userCredential.getEmail());
            loginRequest.setPassword(registrationRequest.getPassword());
            return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg,

                    passwordLogin(loginRequest, Optional.of(userCredential))

                  //  userCredential
            );

        } catch (BadCredentialsException | IllegalArgumentException ex) {


            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(registrationRequest.getEmail());
            loginRequest.setPassword(registrationRequest.getPassword());
            LoginResponse loginResponse = (LoginResponse) handleBadCredential(loginRequest).getBody();
            // res.putAll(setResponseData(loginResponse.isStatus(), loginResponse.getData(), loginResponse.getMessage()));
            return new GeneralResponse(Constant.ResponseCode.WrongUsernameOrPassword.code, Constant.ResponseCode.WrongUsernameOrPassword.msg, null);
        }

    }

    private void saveOTP(UserCredential userCredential, String otpPin) {
        Otp otp = new Otp();
        otp.setOtp(encodeToString(otpPin.getBytes())).setUser(userCredential).setExpirationDate(addMinutesToCurrentDateTime(10, "YYYY-MM-dd HH:mm:ss")).setCreatedBy("System Registration");
        otpRepo.save(otp);
    }

    private void sendOTP(UserCredential userCredential) {
        String password = generatePasswordWithCustomLength(8);
        userCredential.setPassword(passwordEncoder.encode(password));
        saveOTP(userCredential, password);
//        if (userCredential.getNotificationMethod().equals(NOTIFICATION_METHOD.SMS.name())) {
//            System.out.println("OTP");
//            Utility.sendSMS(password);
//        } else if (userCredential.getNotificationMethod().equals(NOTIFICATION_METHOD.EMAIL.name())) {
        EmailDetails emailDetails = new EmailDetails();
        System.out.println("EMAIL");
        emailDetails.setSubject("OTP").setMsgBody(password).setRecipient(userCredential.getEmail());
        String status = sendSimpleMail(emailDetails, javaMailSender);
        System.out.println(status);
        System.out.println(status + " Sending Email");
//        }
    }

    private ExistUser existUser(String email, String phone) {
        ExistUser result = new ExistUser();
        if (userCredentialRepo.findByUniqueFields(email, phone).isPresent()) {
            result.setExist(true).setMessage("User with data exists");
            return result;
        }
        result.setExist(false).setMessage("OK");
        return result;
    }

    @Override
    public GeneralResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) {

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getOldPasswordConfirmation())){
            return new GeneralResponse(Constant.ResponseCode.Validation.code, Constant.ResponseCode.Validation.msg, null);
        }

        ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();
        Optional<UserCredential> userCredential = userCredentialRepo.findById(id);
        if (userCredential.isPresent()) {
            UserCredential user = userCredential.get();
            if (passwordEncoder.matches(decodeToString(changePasswordRequest.getOldPassword()), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(decodeToString(changePasswordRequest.getNewPassword())));
                changePasswordResponse.setData(data);
                userCredentialRepo.save(user);
                return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, changePasswordResponse);
            } else {
                changePasswordResponse.setData(null);
            }
            return new GeneralResponse(Constant.ResponseCode.Validation.code, Constant.ResponseCode.Validation.msg, changePasswordResponse);
        }
        changePasswordResponse.setData(null);
        return new GeneralResponse(Constant.ResponseCode.UserNotFound.code, Constant.ResponseCode.UserNotFound.msg, changePasswordResponse);
    }

    @Override
    public GeneralResponse resetPassword(Long id, ResetPasswordRequest resetPasswordRequest) {
        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
        Optional<UserCredential> optionalUser = userCredentialRepo.findById(id);
        if (optionalUser.isPresent()) {
            UserCredential user = optionalUser.get();
            sendOTP(user);
            user.setPassword("");
            return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, resetPasswordResponse);
        }
        return new GeneralResponse(Constant.ResponseCode.UserNotFound.code, Constant.ResponseCode.UserNotFound.msg, null);
    }

    @Override
    public GeneralResponse verifyOtp(OtpVerifyRequest otpVerifyRequest) throws ParseException {
        OtpVerifyResponse otpVerifyResponse = new OtpVerifyResponse();
        Optional<Otp> otp = otpRepo.findByParams(otpVerifyRequest.getUserId(), otpVerifyRequest.getOtp());
        Optional<UserCredential> userCredential = userCredentialRepo.findById(otpVerifyRequest.getUserId());
        if (otp.isPresent() && userCredential.isPresent()) {
            UserCredential user = userCredential.get();
            return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, sendExpirationResponse(otp.get(), user));
        }
        // otpVerifyResponse.setData(null);
        return new GeneralResponse(Constant.ResponseCode.UserOrOtpNotFound.code, Constant.ResponseCode.UserOrOtpNotFound.msg, null);
    }

    private OtpVerifyResponse sendExpirationResponse(Otp otp, UserCredential userCredential) throws ParseException {
        OtpVerifyResponse otpVerifyResponse = new OtpVerifyResponse();
        Date date = new Date();
        Date otpExpire = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(otp.getExpirationDate());

        if (date.compareTo(otpExpire) < 0) {
            otpVerifyResponse.setData(data);
            userCredential.setStatus(USER_STATUS.ACTIVE.name()).setOtp(false);
            otpRepo.delete(otp);
            return otpVerifyResponse;
        }
        otpVerifyResponse.setData(data);
        return otpVerifyResponse;
    }

    @Override
    public GeneralResponse forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        ForgetPasswordResponse forgetPasswordResponse = new ForgetPasswordResponse();
        Map<String, Object> data = new HashMap<>();
        Optional<UserCredential> userCredential = userCredentialRepo.findByEmail(forgetPasswordRequest.getEmail());
        if (userCredential.isPresent()) {
            UserCredential user = userCredential.get();
            String password = generatePasswordWithCustomLength(8);
            EmailDetails emailDetails = new EmailDetails();
            System.out.println("EMAIL");
            emailDetails.setSubject("OTP").setMsgBody("Your new pass word is " + password + ".\n This password is valid for the next 10 minutes").setRecipient(user.getEmail());
            String status = sendSimpleMail(emailDetails, javaMailSender);
            System.out.println(status + " Sending Email");
            user.setPassword(passwordEncoder.encode(password)).setOtp(true);
            saveOTP(user, password);
            forgetPasswordResponse.setData(data);
            return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, forgetPasswordResponse);
        }
        forgetPasswordResponse.setData(data);
        return new GeneralResponse(Constant.ResponseCode.UserNotFound.code, Constant.ResponseCode.UserNotFound.msg, forgetPasswordResponse);

    }

    @Transactional
    @Override
    public GeneralResponse lock(Long id) {
        UserCredential userCredential = userCredentialRepo.findById(id).orElseThrow(() -> new IllegalStateException("User with" + id + "dose not exist"));
        userCredential.setLock(false);
        return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, null);
    }

    @Transactional
    @Override
    public GeneralResponse unlock(Long id) {
        UserCredential userCredential = userCredentialRepo.findById(id).orElseThrow(() -> new IllegalStateException("User with" + id + "dose not exist"));
        userCredential.setLock(true);
        return new GeneralResponse(Constant.ResponseCode.Success.code, Constant.ResponseCode.Success.msg, null);
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean checkLastTwo(Long userId) {
        List<LoginHistory> loginHistories = loginHistoryRepo.findLastSuccess(userId);
        if (loginHistories.size() >= 3) {
            LoginHistory lastLogin = loginHistories.get(1);
            LoginHistory beforeLastLogin = loginHistories.get(2);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime past = LocalDateTime.now().minusDays(1);
            LocalDateTime checkLastLoginTime = lastLogin.getCreatedOn();
            LocalDateTime checkBeforeLastLoginTime = beforeLastLogin.getCreatedOn();
            if (lastLogin.getStatus().equals(LOGIN_STATUS.FAILED.name()) && beforeLastLogin.getStatus().equals(LOGIN_STATUS.FAILED.name())) {
                return (checkLastLoginTime.isAfter(past) && checkLastLoginTime.isBefore(now)) && (checkBeforeLastLoginTime.isAfter(past) && checkBeforeLastLoginTime.isBefore(now));
            }
        }
        return false;
    }

    @Override
    public GeneralResponse handleBadCredential(LoginRequest loginRequest) {
        Optional<UserCredential> user = userCredentialRepo.findByUsername(loginRequest.getUsername());
        LoginHistory loginHistory = new LoginHistory();
        LoginResponse loginResponse = new LoginResponse();
        if (user.isPresent()) {
            loginHistory.setStatus(LOGIN_STATUS.FAILED.name()).setUserCredential(user.get());
            loginHistoryRepo.save(loginHistory);
            if (checkLastTwo(user.get().getId())) {
                lock(user.get().getId());
            }
            loginResponse.setData(null);
        }
        return new GeneralResponse(Constant.ResponseCode.Validation.code, Constant.ResponseCode.Validation.msg, loginResponse);
    }

    private LoginResponse otpLogin(LoginRequest loginRequest, Optional<UserCredential> userCredentialData) throws ParseException {
        LoginResponse loginResponse = new LoginResponse();
        OtpVerifyRequest otpVerifyRequest = new OtpVerifyRequest();
        otpVerifyRequest.setOtp(loginRequest.getPassword()).setUserId(userCredentialData.get().getId());
        GeneralResponse generalResponse = verifyOtp(otpVerifyRequest);
        OtpVerifyResponse otpVerifyResponse = (OtpVerifyResponse) generalResponse.getBody();
        userLogin(loginRequest, userCredentialData, loginResponse);
        loginResponse.setData(otpVerifyResponse.getData());
        loginResponse.setMessage("Success");

        return loginResponse;
    }

    private LoginResponse passwordLogin(LoginRequest loginRequest, Optional<UserCredential> userCredentialData) {
        LoginResponse loginResponse = new LoginResponse();
        System.out.println("password login ");
        loginResponse = userLogin(loginRequest, userCredentialData, loginResponse);
        return loginResponse.setMessage(null);
    }

    private LoginResponse userLogin(LoginRequest loginRequest, Optional<UserCredential> userCredentialData, LoginResponse loginResponse) {
        loginRequest.setUsername(userCredentialData.get().getEmail());
        System.out.println("user login before auth +" + loginRequest.getPassword() + " "+ loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), decodeToString(loginRequest.getPassword())));

        System.out.println(authentication.getAuthorities());

        if (authentication.isAuthenticated()) {
            System.out.println("authenticatedQQQQQQQQQ");
//            if (loginRequest.getAccessMethod().equals(ACCESS_METHODS.WEB.name())) {
//                data.put("authorized_pages", "/payment,/transfer");
//            }
            List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//            System.out.println("authenticatedQQQQQQQQ   Q  "+authentication.get);

          //  Optional<UserCredential> userCredential = userCredentialRepo.findByEmailOrPhone(authentication.getName());
            System.out.println("authenticatedQQQQQQQQQ");

            LoginHistory loginHistory = new LoginHistory();
            loginHistory.setStatus(LOGIN_STATUS.SUCCESS.name()).setUserCredential(userCredentialData.get());
            loginHistoryRepo.save(loginHistory);
            System.out.println("authenticatedQQQQQQQQQ");

            String access_token = jwtUtils.getAccessToken(loginRequest, "", roles);
            String refresh_token = jwtUtils.getRefreshToken(loginRequest, "", roles);
            UserDataResponse userDataResponse =  userMapper.mapFromUserToLoginResponse(userCredentialData.get());

            System.out.println("USER CRED: "+userCredentialData.get());

            System.out.println("HERE AFTER MAP: "+userDataResponse);

            data.put("user_data", userDataResponse);
            loginResponse.setAccessToken(access_token).setRefreshToken(refresh_token).setData(data);

            return loginResponse;
        }
        return null;
    }
}

