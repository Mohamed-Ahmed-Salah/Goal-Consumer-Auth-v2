package com.gconsumer.GConsumer.controller;

import com.gconsumer.GConsumer.dto.request.*;
import com.gconsumer.GConsumer.service.AuthServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
@CrossOrigin()
public class AuthController {
    private final AuthServiceImp authServiceImp;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) throws ParseException {
        return ResponseEntity.ok().body(authServiceImp.login(loginRequest));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok().body(authServiceImp.registration(registrationRequest));
    }

    @PostMapping("/registration-admin")
    public ResponseEntity<?> registrationForAdmin(@Valid @RequestBody RegistrationRequest registrationRequest) {
        System.out.println("IN CONTROLLLERRRR");
        return ResponseEntity.ok().body(authServiceImp.registration(registrationRequest));
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest passwordRequest) {
        return ResponseEntity.ok().body(authServiceImp.changePassword(id, passwordRequest));
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable Long id, @Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {

        return ResponseEntity.ok().body(authServiceImp.resetPassword(id, resetPasswordRequest));

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerifyRequest otpVerifyRequest) throws ParseException {

        return ResponseEntity.ok().body(authServiceImp.verifyOtp(otpVerifyRequest));

    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) {
        return ResponseEntity.ok().body(authServiceImp.forgetPassword(forgetPasswordRequest));
    }


}


