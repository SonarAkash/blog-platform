package com.primetrade_ai.assignment.Security.Services;


import com.primetrade_ai.assignment.Security.BrevoEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sendinblue.ApiException;

import java.time.Duration;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    private BrevoEmailService emailService;

    public void generateAndSendOtp(String email) {
        
        String otp = String.format("%06d", new Random().nextInt(999999));

        
        redisTemplate.opsForValue().set("otp:" + email, otp, Duration.ofMinutes(5));

       
        try {
            emailService.sendOtpEmail(email, otp);
        } catch (ApiException e) {

            logger.error("Failed to send otp : " + e.getMessage());
        }
    }

    public boolean validateOtp(String email, String otpInput) {
        String storedOtp = redisTemplate.opsForValue().get("otp:" + email);
        return storedOtp != null && storedOtp.equals(otpInput);
    }
}
