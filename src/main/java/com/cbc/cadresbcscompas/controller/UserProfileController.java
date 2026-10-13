package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{email}")
    public ResponseEntity<?> getProfile(@PathVariable String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if (user.getStudentId() == null || user.getStudentId().isEmpty()) {
                user.setStudentId("CBC-" + (int)(Math.random() * 900000 + 100000));
                userRepository.save(user);
            }
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(payload.containsKey("name")) user.setName(payload.get("name"));
            if(payload.containsKey("phone")) user.setPhone(payload.get("phone"));
            if(payload.containsKey("university")) user.setUniversity(payload.get("university"));
            if(payload.containsKey("studentStatus")) user.setStudentStatus(payload.get("studentStatus"));
            if(payload.containsKey("statusYear")) user.setStatusYear(payload.get("statusYear"));
            
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body("User not found");
    }
}
