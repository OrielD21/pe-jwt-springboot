package com.practice.jwt.repository;

import com.practice.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //TODO: if necessary, add the methods

    Optional<User> findByUsernameOrEmail(String userName, String email);


}
