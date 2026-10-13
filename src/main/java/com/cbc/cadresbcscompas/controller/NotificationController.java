package com.cbc.cadresbcscompas.controller;

import com.cbc.cadresbcscompas.model.Notification;
import com.cbc.cadresbcscompas.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    // ইউজারের সব নোটিফিকেশন আনা
    @GetMapping("/{email}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String email) {
        return ResponseEntity.ok(notificationRepository.findForUser(email));
    }

    // নোটিফিকেশন রিড (Read) মার্ক করা
    @PostMapping("/mark-read/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        notificationRepository.findById(id).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
        return ResponseEntity.ok("Marked as read");
    }

    // নতুন নোটিফিকেশন পাঠানো (শুধু অ্যাডমিনদের জন্য)
    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody Notification notification) {
        if(notification.getTargetUserEmail() == null || notification.getTargetUserEmail().isEmpty()){
            notification.setTargetUserEmail("ALL");
        }
        return ResponseEntity.ok(notificationRepository.save(notification));
    }
}
