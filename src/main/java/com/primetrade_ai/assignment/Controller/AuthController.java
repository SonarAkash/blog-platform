package com.primetrade_ai.assignment.Controller;


import com.primetrade_ai.assignment.Model.User;
import com.primetrade_ai.assignment.Payload.Request.LoginRequest;
import com.primetrade_ai.assignment.Payload.Request.SignupRequest;
import com.primetrade_ai.assignment.Payload.Request.VerifyOtpRequest;
import com.primetrade_ai.assignment.Payload.Response.JwtResponse;
import com.primetrade_ai.assignment.Payload.Response.MessageResponse;
import com.primetrade_ai.assignment.Repository.UserRepository;
import com.primetrade_ai.assignment.Security.Jwt.JwtUtils;
import com.primetrade_ai.assignment.Security.Services.OtpService;
import com.primetrade_ai.assignment.Security.Services.UserDetailsImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    OtpService otpService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        
        String jwt = jwtUtils.generateJwtToken(authentication);

        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        System.err.println("mail : " + loginRequest.getEmail());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getIsAdmin(),
                userDetails.getAvatar()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

       
        User user = new User();
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(signUpRequest.getLastname());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setIsAdmin(signUpRequest.getIsAdmin() != null && signUpRequest.getIsAdmin());
        user.setIsVerified(false); 

        user.setAvatar(signUpRequest.getAvatar());
        System.out.println("user : " + user.toString());



        userRepository.save(user);

       
        otpService.generateAndSendOtp(user.getEmail());

        return ResponseEntity.ok(new MessageResponse("User registered! Please check your email for OTP."));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyOtpRequest request) {
        boolean isValid = otpService.validateOtp(request.getEmail(), request.getOtp());

        if (isValid) {
           
            User user = userRepository.findByEmail(request.getEmail()) 
                    .orElseThrow(() -> new RuntimeException("User not found"));
            

            user.setIsVerified(true);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("Account verified successfully!"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid or Expired OTP!"));
        }
    }

}
