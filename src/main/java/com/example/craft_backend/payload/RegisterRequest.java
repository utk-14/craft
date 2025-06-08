package com.example.craft_backend.payload;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role;  // "ADMIN", "SELLER", or "BUYER"
}
