package com.silksoftgroup.otpservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.silksoftgroup.otpservice.domain.utils.Smsc;
import com.twilio.http.TwilioRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtpConfig {

    private static final String ACCOUNT_SID = "";
    private static final String AUTH_TOKEN = "";
    private static final String AWS_ACCESS_KEY = "";
    private static final String AWS_PRIVATE_KEY = "";

    @Bean
    TwilioRestClient twilioRestClient() {

        return new TwilioRestClient.Builder(
                ACCOUNT_SID,
                AUTH_TOKEN
        ).build();
    }

    @Bean
    AmazonSNSClient snsClient() {

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_PRIVATE_KEY);
        AmazonSNS amazonSNS = AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.EU_NORTH_1)
                .build();

        return (AmazonSNSClient) amazonSNS;
    }

    @Bean
    Smsc smsc() {
        return new Smsc();
    }

}
