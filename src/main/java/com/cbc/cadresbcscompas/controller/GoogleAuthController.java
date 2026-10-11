package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.dto.GoogleLoginRequest;
import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.model.Role;
import com.cbc.cadresbcscompas.repository.UserRepository;
import com.cbc.cadresbcscompas.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class GoogleAuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody GoogleLoginRequest request) {
        
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email is required from Google.");
        }

        // ডাটাবেসে চেক করা হচ্ছে ইউজার আগে থেকে আছে কি না
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());
        User user;

        if (existingUserOpt.isPresent()) {
            // ইউজার থাকলে তাকেই রিটার্ন করবে
            user = existingUserOpt.get();
        } else {
            // নতুন ইউজার হলে ডাটাবেসে সেভ করবে
            user = new User();
            user.setName(request.getName() != null && !request.getName().isEmpty() ? request.getName() : "Google User");
            user.setEmail(request.getEmail());
            // গুগল লগইনের জন্য একটি রেন্ডম পাসওয়ার্ড সেট করা হলো
            user.setPassword("GOOGLE_AUTH_" + request.getUid()); 
            user.setRole(Role.STUDENT); 
            userRepository.save(user);
        }

        // আসল JWT টোকেন তৈরি করা হচ্ছে
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        
        // ফ্রন্টএন্ডের রিকোয়ারমেন্ট অনুযায়ী রেসপন্স পাঠানো হচ্ছে
        String responseMessage = "Success Token: " + token + " Role: " + user.getRole().name();
        
        return ResponseEntity.ok(responseMessage);
    }
}
