package org.yigitcanyontem.clients.notification;

import java.time.LocalDateTime;

public record NotificationDto(
        String message,
        boolean isRead,
        LocalDateTime createdAt
) {
}
