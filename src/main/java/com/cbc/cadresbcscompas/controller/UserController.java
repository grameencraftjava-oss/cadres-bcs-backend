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
        try {
            String email = payload.get("email");
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Email is required.");
            }
            if (userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.badRequest().body("Error: Email already exists!");
            }
            User user = new User();
            user.setName(payload.get("name"));
            user.setEmail(email);
            user.setPhone(payload.get("phone"));
            user.setPassword(payload.get("password"));
            user.setRole(Role.STUDENT);
            userRepository.save(user);

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return ResponseEntity.ok("Success Token: " + token + " Role: " + user.getRole().name());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("DB_CRASH: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");

            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
                User user = userOpt.get();
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
                return ResponseEntity.ok("Success Token: " + token + " Role: " + user.getRole().name());
            }
            return ResponseEntity.badRequest().body("Error: Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("DB_CRASH: " + e.getMessage());
        }
    }

    // SECRET ENDPOINT: Make any user an ADMIN
    @GetMapping("/make-admin")
    public ResponseEntity<?> makeAdmin(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Tries to set ADMIN. If your enum is SUPERADMIN, change Role.ADMIN to Role.SUPERADMIN
            user.setRole(Role.ADMIN); 
            userRepository.save(user);
            return ResponseEntity.ok("Success: User " + email + " is now an ADMIN!");
        }
        return ResponseEntity.badRequest().body("Error: User not found in database!");
    }
}
