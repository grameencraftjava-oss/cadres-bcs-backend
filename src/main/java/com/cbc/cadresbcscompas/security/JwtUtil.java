package com.cbc.cadresbcscompas.security;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.UUID;

@Component
public class JwtUtil {
    // pom.xml এ কোনো বাড়তি ডিপেন্ডেন্সি ছাড়াই যেন কাজ করে, তাই Base64 Token ব্যবহার করা হলো
    public String generateToken(String email, String role) {
        String rawToken = email + ":" + role + ":" + UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(rawToken.getBytes());
    }
}
