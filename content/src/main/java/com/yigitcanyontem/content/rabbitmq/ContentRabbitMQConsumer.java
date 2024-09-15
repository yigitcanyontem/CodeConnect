package com.yigitcanyontem.content.rabbitmq;

import com.yigitcanyontem.content.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.yigitcanyontem.clients.shared.dto.GenericRabbitMQMessage;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContentRabbitMQConsumer {

    private final TopicService topicService;

    @RabbitListener(queues = "${rabbitmq.queues.content}")
    public void consumeContentQueue(GenericRabbitMQMessage genericRabbitMQMessage) {
        log.info("Consuming message: {}", genericRabbitMQMessage);
        if (genericRabbitMQMessage.getEndpoint().equals("api/v1/content/topic-increment-view")) {
            topicService.incrementViewCount(Long.valueOf(genericRabbitMQMessage.getMessage().toString()));
        }
    }
}
