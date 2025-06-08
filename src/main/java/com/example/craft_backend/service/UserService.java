package com.example.craft_backend.service;

import com.example.craft_backend.model.User;
import com.example.craft_backend.payload.RegisterRequest;
import com.example.craft_backend.repository.UserRepository;
import com.example.craft_backend.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

   public User registerUser(RegisterRequest request) {
    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
        throw new RuntimeException("Username already taken");
    }

    // Validate role input safely with null check
    String roleInput = request.getRole();
    if (roleInput == null || roleInput.isBlank()) {
        throw new RuntimeException("Role must be provided");
    }
    roleInput = roleInput.toUpperCase();

    if (!List.of("ADMIN", "SELLER", "BUYER").contains(roleInput)) {
        throw new RuntimeException("Invalid role: " + roleInput);
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRoles(Collections.singleton(roleInput)); // single role as Set<String>

    return userRepository.save(user);
}

    



    public String loginUser(String username, String password) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // If authentication succeeds, generate JWT token
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user.getUsername());
    }

   
}
