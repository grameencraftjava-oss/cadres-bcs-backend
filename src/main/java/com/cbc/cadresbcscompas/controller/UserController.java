package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.model.Role;
import com.cbc.cadresbcscompas.repository.UserRepository;
import com.cbc.cadresbcscompas.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        
        // চেক করা হচ্ছে ইমেইল আগে থেকেই আছে কিনা
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email already exists!");
        }
        
        // নতুন ইউজার সেভ করা হচ্ছে
        User user = new User();
        user.setName(payload.get("name"));
        user.setEmail(email);
        user.setPhone(payload.get("phone"));
        user.setPassword(payload.get("password")); // ভবিষ্যতে এটি BCrypt দিয়ে এনক্রিপ্ট করা যাবে
        user.setRole(Role.STUDENT);
        
        userRepository.save(user);
        
        // টোকেন জেনারেট করে ফ্রন্টএন্ডে পাঠানো হচ্ছে
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok("Success Token: " + token + " Role: " + user.getRole().name());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        Optional<User> userOpt = userRepository.findByEmail(email);
        
        // ইমেইল এবং পাসওয়ার্ড ম্যাচ করলে লগইন সাকসেস
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return ResponseEntity.ok("Success Token: " + token + " Role: " + user.getRole().name());
        }
        
        return ResponseEntity.badRequest().body("Error: Invalid email or password");
    }
}
