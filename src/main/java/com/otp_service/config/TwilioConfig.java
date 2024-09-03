package com.otp_service.config;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Bean
    public PhoneNumberUtil phoneNumberUtil(){
        return PhoneNumberUtil.getInstance();
    }
    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String phoneNumber;

    public String getAccountSid() {
        return accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPhoneNUmber() {
        return phoneNumber;
    }
}
