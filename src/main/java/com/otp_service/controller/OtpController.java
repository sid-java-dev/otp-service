package com.otp_service.controller;

import com.otp_service.service.OTPService;
import com.otp_service.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {
@Autowired
private OTPService otpService;

 @PostMapping("/generate")
private ResponseEntity<String> generateOtp(@RequestParam String phoneNumber){
   String result=otpService.generateOtp(phoneNumber);
   return ResponseEntity.ok(result);
}

@PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber,@RequestParam String otpCode){
    boolean valid = otpService.verifyOtp(phoneNumber, otpCode);
    if(valid){
        return new ResponseEntity<>("otp verification successful", HttpStatus.OK);
    }else{
        return new ResponseEntity<>("\"Invalid OTP\"", HttpStatus.UNAUTHORIZED);
    }
}
}
