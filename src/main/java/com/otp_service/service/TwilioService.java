package com.otp_service.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber;
import com.otp_service.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import com.google.i18n.phonenumbers.PhoneNumberUtil;


import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final PhoneNumberUtil phoneNumberUtil;
    private final TwilioConfig twilioConfig;

    public TwilioService(PhoneNumberUtil phoneNumberUtil, TwilioConfig twilioConfig) {
        this.phoneNumberUtil = phoneNumberUtil;
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    public void sendOtp( String toPhoneNumber,String otp){
        // Ensure the phone number is in E.164 format
        toPhoneNumber=toPhoneNumber.startsWith("+91")?toPhoneNumber:"+91"+toPhoneNumber;

        if (!isValidPhoneNumber(toPhoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        // Ensure the phone number is in E.164 format
        toPhoneNumber = formatPhoneNumber(toPhoneNumber);
        System.out.println(toPhoneNumber);
        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(twilioConfig.getPhoneNUmber()),
                        "Your OTP is: " + otp)
                .create();

        // Output the message SID (useful for logging or tracking)
        System.out.println("SMS sent with SID: " + message.getSid());
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
    }
    }

