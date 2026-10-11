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
        try {
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Email is required from Google.");
            }

            Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());
            User user;

            if (existingUserOpt.isPresent()) {
                user = existingUserOpt.get();
            } else {
                user = new User();
                user.setName(request.getName() != null && !request.getName().isEmpty() ? request.getName() : "Unknown");
                user.setEmail(request.getEmail());
                user.setPassword("GOOGLE_AUTH_" + request.getUid());
                user.setRole(Role.STUDENT);
                userRepository.save(user);
            }

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            String responseMessage = "Success Token: " + token + " Role: " + user.getRole().name();
            return ResponseEntity.ok(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("DB_CRASH: " + e.getMessage());
        }
    }
}
