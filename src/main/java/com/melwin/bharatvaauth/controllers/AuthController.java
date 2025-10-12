package com.melwin.bharatvaauth.controllers;

import com.melwin.bharatvaauth.dto.JwtResponseDto;
import com.melwin.bharatvaauth.dto.LoginRequestDto;
import com.melwin.bharatvaauth.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        ));
        String jwtToken = jwtService.generateToken(loginRequestDto.getEmail().trim());
        return ResponseEntity.ok().body(new JwtResponseDto(jwtToken));
    }

   @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/password!");
    }
}
