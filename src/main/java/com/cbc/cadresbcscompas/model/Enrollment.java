package com.cbc.cadresbcscompas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;
    private String studentEmail;
    private String studentName;
    private String studentPhone;
    private String courseId;
    private String courseTitle;
    private String instructorName;
    private Double amountPaid;
    private String paymentMethod;
    private String trxId;
    private LocalDateTime enrollmentDate = LocalDateTime.now();
    
    // ম্যানুয়াল এনরোলমেন্টের ক্ষেত্রে অ্যাডমিনের তথ্য
    private String enrolledByAdminEmail;
    private String enrolledByAdminName;

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getStudentPhone() { return studentPhone; }
    public void setStudentPhone(String studentPhone) { this.studentPhone = studentPhone; }
    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public Double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTrxId() { return trxId; }
    public void setTrxId(String trxId) { this.trxId = trxId; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDateTime enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getEnrolledByAdminEmail() { return enrolledByAdminEmail; }
    public void setEnrolledByAdminEmail(String enrolledByAdminEmail) { this.enrolledByAdminEmail = enrolledByAdminEmail; }
    public String getEnrolledByAdminName() { return enrolledByAdminName; }
    public void setEnrolledByAdminName(String enrolledByAdminName) { this.enrolledByAdminName = enrolledByAdminName; }
}
