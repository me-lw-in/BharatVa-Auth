package com.melwin.bharatvaauth.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

}
