package com.cbc.cadresbcscompas.service;

import com.cbc.cadresbcscompas.dto.RegisterRequest;
import com.cbc.cadresbcscompas.dto.LoginRequest;
import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.repository.UserRepository;
import com.cbc.cadresbcscompas.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return "Error: Email is already registered!";
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setPhone(request.getPhone());
        
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
            newUser.setRole("ADMIN");
        } else {
            newUser.setRole("STUDENT");
        }

        userRepository.save(newUser);
        return "Success: User registered successfully as " + newUser.getRole() + "!";
    }

    public String loginUser(LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        
        if (user.isEmpty()) {
            return "Error: User not found!";
        }

        if (passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            // UPDATE: টোকেন জেনারেট করার সময় ইউজারের ইমেইলের সাথে রোলটাও দিয়ে দেওয়া হলো
            String token = jwtUtil.generateToken(user.get().getEmail(), user.get().getRole());
            return "Success: Login successful!\nToken: " + token;
        } else {
            return "Error: Incorrect password!";
        }
    }
}
