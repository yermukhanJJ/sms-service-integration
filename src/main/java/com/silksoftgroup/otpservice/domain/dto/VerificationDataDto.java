package com.silksoftgroup.otpservice.domain.dto;

import lombok.Data;

@Data
public class VerificationDataDto {

    private String phoneNumber;

    private String code;
}
