package com.melwin.bharatvaauth.services;

import com.melwin.bharatvaauth.dto.UserRegisterDto;
import com.melwin.bharatvaauth.entities.User;
import com.melwin.bharatvaauth.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Boolean createUser(UserRegisterDto userRegisterDto){
        if (
                userRepository.existsByEmail(userRegisterDto.getEmail().trim()) ||
                        userRepository.existsByUsername(userRegisterDto.getUserName().trim()) ||
                        userRepository.existsByPhoneNumber(userRegisterDto.getPhoneNumber().trim())
        ){
            return false;
        }else {
            User newUser = new User();
            newUser.setName(userRegisterDto.getName().trim());
            newUser.setUsername(userRegisterDto.getUserName().trim());
            newUser.setEmail(userRegisterDto.getEmail().trim());
            newUser.setPhoneNumber(userRegisterDto.getPhoneNumber().trim());
            newUser.setPasswordHash(passwordEncoder.encode(userRegisterDto.getPassword().trim()));
            String otp = mailService.sendOtpToMail(userRegisterDto.getEmail().trim());
            newUser.setOtp(otp);
            newUser.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
            userRepository.save(newUser);
            return true;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                Collections.emptyList()
        );
    }
}
