package com.craftstore.craft_backend.controller;

import com.craftstore.craft_backend.model.User;
import com.craftstore.craft_backend.payload.UpdateProfileRequest;
import com.craftstore.craft_backend.payload.UserProfileResponse;
import com.craftstore.craft_backend.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());

        // Pick the first role from the set (assuming one role per user)
        String role = user.getRoles().stream().findFirst().orElse("UNKNOWN");

        UserProfileResponse response = new UserProfileResponse(
                user.getUsername(),
                user.getEmail(),
                role);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateProfileRequest request) {
        userService.updateUserProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    @GetMapping("/users/pending-sellers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getPendingSellers() {
        List<User> pending = userService.getPendingSellers();
        return ResponseEntity.ok(pending);
    }

    @PutMapping("/approve/{sellerId}")
    public ResponseEntity<?> approveSeller(@PathVariable Long sellerId) {
        User updatedUser = userService.approveSeller(sellerId);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/suspend/{userId}")
    public ResponseEntity<?> suspendUser(@PathVariable Long userId) {
        User updatedUser = userService.suspendUser(userId);
        return ResponseEntity.ok(updatedUser);
    }

}
