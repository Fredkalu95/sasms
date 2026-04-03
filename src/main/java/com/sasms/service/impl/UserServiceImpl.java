package com.sasms.service.impl;

import com.sasms.model.dto.LoginRequest;
import com.sasms.model.dto.RegisterRequest;
import com.sasms.model.entity.Role;
import com.sasms.model.entity.User;
import com.sasms.repository.RoleRepository;
import com.sasms.repository.UserRepository;
import com.sasms.security.JwtService;
import com.sasms.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(RegisterRequest request) {
        Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password")
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid email or password");
        }

        // Extract role names
        Set<String> roles = user.getRoles()
                .stream()
                .map(Role::getName) // Assuming Role.java has a getName() method
                .collect(Collectors.toSet());

        // Generate token with username + roles
        return jwtService.generateToken(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
