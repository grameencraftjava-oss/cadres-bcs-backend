package com.cbc.cadresbcscompas.repository;

import com.cbc.cadresbcscompas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // ইমেইল দিয়ে ইউজার খোঁজার জন্য
}