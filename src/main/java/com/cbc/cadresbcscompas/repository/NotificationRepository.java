package com.cbc.cadresbcscompas.repository;

import com.cbc.cadresbcscompas.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.targetUserEmail = ?1 OR n.targetUserEmail = 'ALL' ORDER BY n.createdAt DESC")
    List<Notification> findForUser(String email);
}
