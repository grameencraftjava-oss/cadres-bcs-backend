package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.dto.RegisterRequest;
import com.cbc.cadresbcscompas.dto.LoginRequest;
import com.cbc.cadresbcscompas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
}