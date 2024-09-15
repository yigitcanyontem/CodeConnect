package org.yigitcanyontem.clients.notification;

public record NotificationCreateDto(
        Integer userId,
        String message
) {
}
