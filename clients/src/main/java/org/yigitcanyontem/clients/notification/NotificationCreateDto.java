package org.yigitcanyontem.clients.notification;

public record NotificationCreateDto(
        Integer customerId,
        String message
) {
}
