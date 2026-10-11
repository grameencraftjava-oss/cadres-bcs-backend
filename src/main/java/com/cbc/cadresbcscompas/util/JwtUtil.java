package com.cbc.cadresbcscompas.util;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.UUID;

@Component
public class JwtUtil {
    public String generateToken(String email, String role) {
        String rawToken = email + ":" + role + ":" + UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(rawToken.getBytes());
    }
}
