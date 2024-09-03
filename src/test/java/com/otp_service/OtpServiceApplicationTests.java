package com.otp_service;

import com.otp_service.service.OTPService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@SpringBootTest
class OtpServiceApplicationTests {
//	@Autowired
//	private RedisTemplate redisTemplate;
	@Autowired
	private OTPService otpService;

	@Test
	@DisplayName("generate otp")
	public void generateOtpTest(){
		String phoneNumber="7621889611";
        otpService.generateOtp(phoneNumber);
	}

//	@Test
//	@Disabled
//	void save_redis_test() {
//		redisTemplate.opsForValue().set("email","gmail@gmail.com", Duration.ofMinutes(5));
//		Object email = redisTemplate.opsForValue().get("email");
//		Assertions.assertEquals("gmail@gmail.com",email);
//	}


}
