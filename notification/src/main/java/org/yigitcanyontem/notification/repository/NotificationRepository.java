package org.yigitcanyontem.notification.repository;

import org.yigitcanyontem.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Integer> {
}
