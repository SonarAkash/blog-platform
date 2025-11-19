package com.primetrade_ai.assignment.Payload.Request;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String otp;
}
