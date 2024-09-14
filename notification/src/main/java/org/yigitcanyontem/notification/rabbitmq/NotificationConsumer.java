package org.yigitcanyontem.notification.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.yigitcanyontem.clients.notification.NotificationCreateDto;
import org.yigitcanyontem.notification.service.NotificationService;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationCreateDto notificationRequest) {
        log.info("Consuming message: {}", notificationRequest);
        notificationService.sendNotification(notificationRequest);
    }
}
