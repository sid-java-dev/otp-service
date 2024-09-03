package com.otp_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TwilioService twilioService;

    private static final String OTP_PREFIX = "otp:";
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";
    private static final int RATE_LIMIT =5;
    private static final long RATE_LIMIT_PERIOD_SECONDS = 60;


    public String generateOtp(String phoneNumber) {

        // Check rate limiting
        String rateLimitKey = RATE_LIMIT_PREFIX + phoneNumber;
        if (!isRateLimited(rateLimitKey)) {
            return "Rate limit exceeded. Please try again later.";
        }
        String otpKey = OTP_PREFIX + phoneNumber;
        String existingOTP = (String) redisTemplate.opsForValue().get(otpKey);
        System.out.println(existingOTP);
        if (existingOTP != null) {
            return "An OTP has already been generated for this number. Please check your SMS.";
        }
        String otp = generateRandomOtp(6);
        redisTemplate.opsForValue().set(otpKey, otp, Duration.ofMinutes(1));
        redisTemplate.opsForValue().get(phoneNumber);
        twilioService.sendOtp(phoneNumber, otp);
        return "otp send successfully on register number";

    }

    public String generateRandomOtp(int digit) {
        if (digit != 6) {
            throw new IllegalArgumentException(" only 6 digit otp is supported");
        }
        Random random = new Random();
        int otp = 100000 + random.nextInt(999999);
        return String.valueOf(otp);
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        String otpKey = OTP_PREFIX + phoneNumber;
        String storedOtp = (String) redisTemplate.opsForValue().get(otpKey);
        if (storedOtp != null && storedOtp.equals(otp)) {
            redisTemplate.delete(otpKey);
            return true;
        }
        return false;
    }

    public boolean isRateLimited(String rateLimitKey) {
        Long currentCount = redisTemplate.opsForValue().increment(rateLimitKey, 1);

        if (currentCount == 1) {
            redisTemplate.expire(rateLimitKey, RATE_LIMIT_PERIOD_SECONDS, TimeUnit.SECONDS);
        }
        return currentCount <= RATE_LIMIT;
    }
}
