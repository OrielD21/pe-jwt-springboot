package com.practice.jwt.service;

import com.practice.jwt.DTO.LoginDto;

public interface AuthService {

    String login(LoginDto loginDto);

}
