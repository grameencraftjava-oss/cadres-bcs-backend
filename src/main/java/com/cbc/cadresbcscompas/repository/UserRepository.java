package com.cbc.cadresbcscompas.repository;

import com.cbc.cadresbcscompas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentId(String studentId); // নতুন সার্চ মেথড
}
