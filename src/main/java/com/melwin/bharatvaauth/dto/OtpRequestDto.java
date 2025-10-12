package com.melwin.bharatvaauth.dto;

import lombok.Data;

@Data
public class OtpRequestDto {
    private String email;
    private String userEnteredOtp;
}
