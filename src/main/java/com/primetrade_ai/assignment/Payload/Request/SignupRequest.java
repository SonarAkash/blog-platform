package com.primetrade_ai.assignment.Payload.Request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private String firstname;
    private String lastname;

    // Optional: Allow setting admin status during signup for this demo
    // (In real life, you wouldn't expose this, but it helps for the assignment demo)
    private Boolean isAdmin;
}
