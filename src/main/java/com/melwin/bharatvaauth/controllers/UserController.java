package com.melwin.bharatvaauth.controllers;

import com.melwin.bharatvaauth.dto.OtpRequestDto;
import com.melwin.bharatvaauth.dto.UserRegisterDto;
import com.melwin.bharatvaauth.enums.UserStatus;
import com.melwin.bharatvaauth.repositories.UserRepository;
import com.melwin.bharatvaauth.services.MailService;
import com.melwin.bharatvaauth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final MailService mailService;
    private final UserRepository userRepository;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserRegisterDto userRegisterDto) {
        var isSuccess = userService.createUser(userRegisterDto);
        if (isSuccess) {
            Map<String,String> response = new HashMap<>();
            response.put("status", "pending");
            response.put("message", "Account created successfully. Please verify your email with the OTP sent.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exist!");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequestDto otpRequestDto) {
        String enteredOtp = String.valueOf(otpRequestDto.getUserEnteredOtp());
        String email = otpRequestDto.getEmail().trim();
        var user = userRepository.findByEmail(email);
        if (user.getOtp().equals(enteredOtp) && user.getOtpExpiry().isAfter(LocalDateTime.now())){
            user.setOtpExpiry(null);
            user.setOtp(null);
            user.setStatus(UserStatus.CREATED);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("Account verified successfully");
        }else if(user.getOtp().equals(enteredOtp) && user.getOtpExpiry().isBefore(LocalDateTime.now())){
            var otp =  mailService.sendOtpToMail(email);
            user.setOtp(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Otp was expired. Sent it again!");
        }else{
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP! Please try again.");
        }
    }
}
