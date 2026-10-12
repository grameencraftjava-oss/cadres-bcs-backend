package com.cbc.cadresbcscompas.repository;

import com.cbc.cadresbcscompas.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentEmail(String studentEmail);
    // একই কোর্সে স্টুডেন্ট আগে থেকে আছে কি না তা চেক করার কাস্টম মেথড
    boolean existsByStudentEmailAndCourseId(String studentEmail, String courseId);
}
