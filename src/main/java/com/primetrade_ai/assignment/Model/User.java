package com.primetrade_ai.assignment.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data // Generates Getters, Setters, toString, etc.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Will store the BCrypt hash

    private String avatar; // Stores the URL/Path to the image

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "is_verified")
    private Boolean isVerified = false; // Default is false
}