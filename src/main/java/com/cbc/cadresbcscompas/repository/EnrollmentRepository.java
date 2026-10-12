package com.cbc.cadresbcscompas.repository;

import com.cbc.cadresbcscompas.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // ইউজারের ইমেইল দিয়ে তার সব কেনা কোর্স খুঁজে বের করার কাস্টম মেথড
    List<Enrollment> findByStudentEmail(String studentEmail);
}
