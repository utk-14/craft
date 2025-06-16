// package com.example.craft_backend.payload;

// import lombok.AllArgsConstructor;
// import lombok.Data;

// @Data
// @AllArgsConstructor
// public class AuthResponse {
//     private String token;
//     private String tokenType = "Bearer";
// }
package com.craftstore.craft_backend.payload;

public class AuthResponse {
    private String jwt;
    private String username;
    private String role;

    public AuthResponse(String jwt, String username, String role) {
        this.jwt = jwt;
        this.username = username;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
