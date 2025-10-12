package com.melwin.bharatvaauth.repositories;

import com.melwin.bharatvaauth.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByPhoneNumber(String phone);
    User findByEmail(String email);
}