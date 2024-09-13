package org.yigitcanyontem.notification.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.yigitcanyontem.clients.notification.NotificationCreateDto;
import org.yigitcanyontem.clients.notification.NotificationDto;
import org.yigitcanyontem.notification.entity.Notification;
import org.yigitcanyontem.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationDto getNotification(Integer notificationId) {
        log.info("retrieving notification for id {}", notificationId);
        Notification notification = notificationRepository.getById(notificationId);
        return new NotificationDto(notification.getMessage(), notification.isRead(), notification.getCreatedAt());
    }

    public NotificationDto sendNotification(NotificationCreateDto notificationCreateDto) {
        log.info("sending notification to customer {}", notificationCreateDto.customerId());
        Notification notification = Notification.builder()
                .customerId(notificationCreateDto.customerId())
                .message(notificationCreateDto.message())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        notificationRepository.saveAndFlush(notification);
        log.info("notification sent: {}", notificationCreateDto.customerId());
        return new NotificationDto(notification.getMessage(), notification.isRead(), notification.getCreatedAt());
    }

}
