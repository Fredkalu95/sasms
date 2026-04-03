package com.sasms.service;

import com.sasms.model.dto.LoginRequest;
import com.sasms.model.dto.RegisterRequest;
import com.sasms.model.entity.User;

public interface UserService {

    void register(RegisterRequest request);
    String login(LoginRequest request);

    User findByEmail(String email);

}
