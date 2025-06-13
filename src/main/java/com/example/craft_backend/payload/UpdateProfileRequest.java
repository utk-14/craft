package com.example.craft_backend.payload;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String username;
    private String password;  // Optional: if user wants to change it
}
