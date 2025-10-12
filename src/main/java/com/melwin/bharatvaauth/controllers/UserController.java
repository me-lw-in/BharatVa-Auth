package com.melwin.bharatvaauth.controllers;

import com.melwin.bharatvaauth.dto.UserRegisterDto;
import com.melwin.bharatvaauth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody UserRegisterDto userRegisterDto) {
        var isSuccess = userService.createUser(userRegisterDto);
        if (isSuccess) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User Created Successfully!");
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exist!");
        }
    }
}
