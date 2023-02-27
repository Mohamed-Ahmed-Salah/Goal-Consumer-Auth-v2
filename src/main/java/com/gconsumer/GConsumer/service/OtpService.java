package com.gconsumer.GConsumer.service;

import com.gconsumer.GConsumer.dto.request.OtpRequest;
import com.gconsumer.GConsumer.dto.response.GeneralResponse;

public interface OtpService {

    GeneralResponse save(OtpRequest otpRequest, String createdBy);

    GeneralResponse delete(Long id);

    GeneralResponse update(Long id, OtpRequest otpRequest, String modifiedBy);

    GeneralResponse getOTPs();

    GeneralResponse getOtp(Long id);
}
