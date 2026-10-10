package com.cbc.cadresbcscompas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testApi() {
        return "Welcome to Cadre's BCS Compass Backend! MySQL Database connected successfully.";
    }
}