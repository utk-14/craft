package com.example.craft_backend.repository;

import com.example.craft_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // ✅ Email-based login

    // ✅ Admin API: Get sellers who are not yet approved
    List<User> findByRolesContainingAndApprovedFalse(String role);
}
