package com.melwin.bharatvaauth.services;

import com.melwin.bharatvaauth.dto.UserRegisterDto;
import com.melwin.bharatvaauth.entities.User;
import com.melwin.bharatvaauth.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Boolean createUser(UserRegisterDto userRegisterDto){
        if (
                userRepository.existsByEmail(userRegisterDto.getEmail().trim()) &&
                        userRepository.existsByUsername(userRegisterDto.getUserName().trim()) &&
                        userRepository.existsByPhoneNumber(userRegisterDto.getPhoneNumber().trim())
        ){
            return false;
        }else {
            User newUser = new User();
            newUser.setName(userRegisterDto.getName().trim());
            newUser.setUsername(userRegisterDto.getUserName().trim());
            newUser.setEmail(userRegisterDto.getEmail().trim());
            newUser.setPhoneNumber(userRegisterDto.getPhoneNumber().trim());
            newUser.setPasswordHash(userRegisterDto.getPassword().trim());
            userRepository.save(newUser);
            return true;
        }
    }
}
