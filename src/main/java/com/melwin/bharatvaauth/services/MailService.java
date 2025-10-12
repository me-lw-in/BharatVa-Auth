package com.melwin.bharatvaauth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    private JavaMailSender mailSender;

    public String generateOtp(){
        SecureRandom random = new SecureRandom();
        return  String.valueOf(100000 + random.nextInt(900000));
    }

    public String sendOtpToMail(String toEmail){
        String otp = generateOtp();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(toEmail);
        message.setSubject("Your OTP for registration");
        message.setText("Your OTP is "+ otp + "\nThis otp is available for 5 minutes.");
        mailSender.send(message);
        return otp;
    }
}
