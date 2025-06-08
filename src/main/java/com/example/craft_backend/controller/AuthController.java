package com.example.craft_backend.controller;

import com.example.craft_backend.model.User;
import com.example.craft_backend.payload.RegisterRequest;
import com.example.craft_backend.security.JwtUtil;
import com.example.craft_backend.service.CustomUserDetailsService;
import com.example.craft_backend.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
    User registeredUser = userService.registerUser(request);
    return ResponseEntity.ok(registeredUser);
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            final var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            final String jwt = jwtUtil.generateToken(user.getUsername()); // ✅ pass username as String


            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // DTO for login response
    public static record AuthResponse(String jwt) { }
}
