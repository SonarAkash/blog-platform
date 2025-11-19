package com.primetrade_ai.assignment.Repository;


import com.primetrade_ai.assignment.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Needed for Login: "SELECT * FROM users WHERE username = ?"
    Optional<User> findByUsername(String username);

    // Needed for Registration: Check if user already exists
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
