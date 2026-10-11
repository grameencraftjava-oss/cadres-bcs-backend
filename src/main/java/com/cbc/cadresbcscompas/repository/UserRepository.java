package com.cbc.cadresbcscompas.repository;
import com.cbc.cadresbcscompas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
