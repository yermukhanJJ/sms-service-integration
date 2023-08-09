package com.silksoftgroup.otpservice.controller;

import com.silksoftgroup.otpservice.domain.dto.SendVerificationDto;
import com.silksoftgroup.otpservice.domain.dto.VerificationDataDto;
import com.silksoftgroup.otpservice.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController(value = OtpController.CONTROLLER_PATH)
@RequiredArgsConstructor
public class OtpController {

    public static final String CONTROLLER_PATH = "/twilio";

    private final OtpService otpService;

    @GetMapping("/send")
    public ResponseEntity<Void> send(@RequestBody SendVerificationDto sendVerificationDto) {
        otpService.sendVerificationToken(sendVerificationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerificationDataDto verificationDataDto) {
        return ResponseEntity.ok(otpService.verificationCheck(verificationDataDto.getPhoneNumber(), verificationDataDto.getCode()));
    }

    @GetMapping(value = "/aws/send/{phoneNumber}")
    public ResponseEntity<Void> sendAwsSms(@PathVariable(value = "phoneNumber") String phoneNumber) {
        otpService.sendSmsWithAwsSns(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/smsc/send/{phoneNumber}")
    public ResponseEntity<Void> sendSmsc(@PathVariable(value = "phoneNumber") String phoneNumber) {
        otpService.sendSmsWithSMSC(phoneNumber);
        return ResponseEntity.ok().build();
    }
}
