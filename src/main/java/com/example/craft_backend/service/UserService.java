package com.example.craft_backend.service;

import com.example.craft_backend.model.User;
import com.example.craft_backend.payload.RegisterRequest;
import com.example.craft_backend.payload.UpdateProfileRequest;
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

    // ✅ Register user
    public User registerUser(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

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
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(roleInput));

        return userRepository.save(user);
    }

    // ✅ Get user by email (used in AuthController)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public void updateUserProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);
    }

    public List<User> getPendingSellers() {
        return userRepository.findByRolesContainingAndApprovedFalse("SELLER");
    }

    public User approveSeller(Long sellerId) {
        User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRoles().contains("SELLER")) {
            throw new RuntimeException("User is not a seller");
        }

        user.setApproved(true);
        return userRepository.save(user);
    }

    public User suspendUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setApproved(false);
        return userRepository.save(user);
    }

}
