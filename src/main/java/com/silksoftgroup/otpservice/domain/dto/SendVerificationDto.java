package com.silksoftgroup.otpservice.domain.dto;

import com.silksoftgroup.otpservice.domain.enums.VerificationType;
import lombok.Data;

@Data
public class SendVerificationDto {

    private String phoneNumber;

    private VerificationType verificationType;
}
