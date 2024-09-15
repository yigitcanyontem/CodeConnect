package org.yigitcanyontem.notification.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.yigitcanyontem.clients.notification.NotificationCreateDto;
import org.yigitcanyontem.clients.notification.NotificationDto;
import org.yigitcanyontem.notification.entity.Notification;
import org.yigitcanyontem.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationDto getNotification(Integer notificationId) {
        log.info("retrieving notification for id {}", notificationId);
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new IllegalStateException("Notification with id " + notificationId + " not found")
        );
        notification.setRead(true);
        notification.setReadAt(new Date());
        return new NotificationDto(notification.getMessage(), notification.isRead(), notification.getCreatedAt());
    }

    public NotificationDto sendNotification(NotificationCreateDto notificationCreateDto) {
        log.info("sending notification to user {}", notificationCreateDto.userId());
        Notification notification = Notification.builder()
                .userId(notificationCreateDto.userId())
                .message(notificationCreateDto.message())
                .createdAt(Date.from(LocalDateTime.now().toInstant(java.time.ZoneOffset.UTC)))
                .isRead(false)
                .build();

        notificationRepository.saveAndFlush(notification);
        log.info("notification sent: {}", notificationCreateDto.userId());
        return new NotificationDto(notification.getMessage(), notification.isRead(), notification.getCreatedAt());
    }

}
