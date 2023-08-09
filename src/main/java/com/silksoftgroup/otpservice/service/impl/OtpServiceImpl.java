package com.silksoftgroup.otpservice.service.impl;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.VerifySMSSandboxPhoneNumberRequest;
import com.amazonaws.services.sns.model.VerifySMSSandboxPhoneNumberResult;
import com.silksoftgroup.otpservice.domain.dto.SendVerificationDto;
import com.silksoftgroup.otpservice.domain.dto.VerificationDataDto;
import com.silksoftgroup.otpservice.domain.enums.VerificationType;
import com.silksoftgroup.otpservice.domain.exceptions.BadRequestException;
import com.silksoftgroup.otpservice.domain.utils.Smsc;
import com.silksoftgroup.otpservice.service.OtpService;
import com.twilio.Twilio;
import com.twilio.http.Request;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service(value = OtpServiceImpl.SERVICE_VALUE)
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    public static final String SERVICE_VALUE = "OtpServiceImlp";
    private final TwilioRestClient twilioRestClient;
    private final AmazonSNSClient amazonSNSClient;
    private final Smsc smsc;

    @Override
    public void sendVerificationToken(@NonNull SendVerificationDto sendVerificationDto) {
        Twilio.setRestClient(twilioRestClient);
        switch (sendVerificationDto.getVerificationType()) {
            case SMS -> {
//                Verification verification = Verification.creator(
//                        "VA632efa017e47a0d4f9e7f053ac8f09d6",
//                        sendVerificationDto.getPhoneNumber(),
//                        "sms"
//                ).create();
//                log.info(verification.getStatus() + " " + verification.getChannel());
                Message message = Message.creator(
                                new com.twilio.type.PhoneNumber(sendVerificationDto.getPhoneNumber()),
                                "MG2b847b43ce34bd0ed870ec46bbf04c12",
                                "SILK. Your verification code is: 025509")
                        .create();

                System.out.println(message.getSid());
            }
            case VOICE -> {
                Verification verification = Verification.creator(
                        "VA632efa017e47a0d4f9e7f053ac8f09d6",
                        sendVerificationDto.getPhoneNumber(),
                        "call"
                ).create();
                log.info(verification.getStatus() + " " + verification.getChannel());
            }
            case WHATSAPP -> {
//                Verification verification = Verification.creator(
//                        "VA632efa017e47a0d4f9e7f053ac8f09d6",
//                        sendVerificationDto.getPhoneNumber(),
//                        "whatsapp"
//                ).create();
//                log.info(verification.getStatus() + " " + verification.getChannel());
                Message message = Message.creator(
                                new com.twilio.type.PhoneNumber("whatsapp:" + "+77066543979"),
                                new com.twilio.type.PhoneNumber("whatsapp:" + sendVerificationDto.getPhoneNumber()),
                                "Hello there!")
                        .create();

                System.out.println(message.getSid());
            }
            default -> throw new BadRequestException("Verification type not allowed");
        }
    }

    @Override
    public String verificationCheck(@NonNull String phoneNumber, @NonNull String code) {
        Twilio.setRestClient(twilioRestClient);

        VerificationCheck verificationCheck = VerificationCheck.creator("VA632efa017e47a0d4f9e7f053ac8f09d6")
                .setTo(phoneNumber)
                .setCode(code)
                .create();
        return verificationCheck.getStatus();
    }

//    @Override
//    public HttpResponse<String> sendSMSPinWithBasicAuth(String phoneNumber) throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newBuilder().build();
//
//        String payload = getSMSVerificationRequestBody(phoneNumber);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .POST(HttpRequest.BodyPublishers.ofString(payload))
//                .uri(URI.create(SINCH_URL))
//                .header("Content-Type", JSON_CONTENT_TYPE)
//                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((SINCH_APPLICATION_KEY + ":" + SINCH_PRIVATE_KEY).getBytes()))
//                .build();
//
//        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//    }
//
//    @Override
//    public HttpResponse<String> verifySinchSmsCode(@NonNull String phoneNumber, @NonNull String code) throws IOException, InterruptedException {
//        HttpClient httpClient = HttpClient.newBuilder().build();
//
//        String payload = getPhoneNumberVerifyRequestBody(code);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .POST(HttpRequest.BodyPublishers.ofString(payload))
//                .uri(URI.create(SINCH_URL + "/" + phoneNumber))
//                .header("Content-Type", JSON_CONTENT_TYPE)
//                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((SINCH_APPLICATION_KEY + ":" + SINCH_PRIVATE_KEY).getBytes()))
//                .build();
//
//        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//    }

    @Override
    public void sendSmsWithAwsSns(@NonNull String phoneNumber) {

        PublishRequest publishRequest = new PublishRequest()
                .withPhoneNumber(phoneNumber)
                .withMessage("Hello,dude! 1093482938492");

        PublishResult result = amazonSNSClient.publish(publishRequest);
        System.out.println(result.toString());
    }

    @Override
    public void sendSmsWithSMSC(String phoneNumber) {
        smsc.send_sms(phoneNumber, "Hello there!", 0, "", "", 1, "MINATO", "");
    }

    //    private String getPhoneNumberVerifyRequestBody(String code) {
//        return """
//            {
//                "method": "sms",
//                "sms": {
//                    "code": "%s"
//                }
//            }
//            """.formatted(code);
//    }
//
//    private static String getSMSVerificationRequestBody(String phoneNumber) {
//        return """
//            {
//                "identity": {
//                    "type": "number",
//                    "endpoint": "%s"
//                },
//                "method": "sms"
//            }
//            """.formatted(phoneNumber);
//    }


}
