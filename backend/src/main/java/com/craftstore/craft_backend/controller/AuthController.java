package com.craftstore.craft_backend.controller;

import com.example.craft_backend.model.User;
import com.example.craft_backend.payload.AuthResponse;
import com.example.craft_backend.payload.LoginRequest;
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

    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    //     try {
    //         authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    //         );

    //         User user = userService.getUserByEmail(request.getEmail());
    //         String jwt = jwtUtil.generateToken(user.getEmail());

    //         return ResponseEntity.ok(new AuthResponse(jwt, user.getUsername(), user.getRole()));
    //     } catch (AuthenticationException e) {
    //         return ResponseEntity.status(401).body("Invalid username or password");
    //     }
    // }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userService.getUserByEmail(request.getEmail());
        String jwt = jwtUtil.generateToken(user.getEmail());

        // ✅ Get a single role from the set (assuming one role per user)
        String role = user.getRoles().iterator().next(); 

        return ResponseEntity.ok(new AuthResponse(jwt, user.getUsername(), role));
    } catch (AuthenticationException e) {
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}

}
