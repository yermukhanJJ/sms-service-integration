package com.silksoftgroup.otpservice.service;

import com.silksoftgroup.otpservice.domain.dto.SendVerificationDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;

@Service
public interface OtpService {

    void sendVerificationToken(@NonNull SendVerificationDto sendVerificationDto);

    String verificationCheck(@NonNull String phoneNumber, @NonNull String code);

//    HttpResponse<String> sendSMSPinWithBasicAuth(String phoneNumber) throws IOException, InterruptedException;
//
//    HttpResponse<String> verifySinchSmsCode(@NonNull String phoneNumber, @NonNull String code) throws IOException, InterruptedException;

    void sendSmsWithAwsSns(@NonNull String phoneNumber);

    void sendSmsWithSMSC(@NonNull String phoneNumber);

}
