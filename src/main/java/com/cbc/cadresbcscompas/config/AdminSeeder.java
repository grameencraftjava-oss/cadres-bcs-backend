package com.cbc.cadresbcscompas.config;

import com.cbc.cadresbcscompas.model.User;
import com.cbc.cadresbcscompas.model.Role;
import com.cbc.cadresbcscompas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // এই ইমেইলটিকে সার্ভার স্টার্ট হওয়ার সাথে সাথেই সুপার অ্যাডমিন বানিয়ে দেওয়া হবে
        String adminEmail = "admin@cbc.com";
        Optional<User> userOpt = userRepository.findByEmail(adminEmail);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // যদি আগে থেকে সুপার অ্যাডমিন না থাকে, তবেই আপডেট করবে
            if (user.getRole() != Role.SUPERADMIN) {
                user.setRole(Role.SUPERADMIN);
                userRepository.save(user);
                System.out.println("✅ SUPERADMIN role assigned to: " + adminEmail);
            }
        }
    }
}
