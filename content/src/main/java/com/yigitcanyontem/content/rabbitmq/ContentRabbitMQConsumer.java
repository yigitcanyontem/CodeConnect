package com.yigitcanyontem.content.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yigitcanyontem.content.service.ReplyService;
import com.yigitcanyontem.content.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.yigitcanyontem.clients.shared.dto.GenericRabbitMQMessage;
import org.yigitcanyontem.clients.shared.dto.GenericResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContentRabbitMQConsumer {

    private final ObjectMapper objectMapper;
    private final TopicService topicService;
    private final ReplyService replyService;

    @RabbitListener(queues = "${rabbitmq.queues.content}")
    public void consumeContentQueue(GenericRabbitMQMessage genericRabbitMQMessage) {
        log.info("Consuming message: {}", genericRabbitMQMessage);
        if (genericRabbitMQMessage.getEndpoint().equals("api/v1/content/topic-increment-view")) {
            topicService.incrementViewCount(Long.valueOf(genericRabbitMQMessage.getMessage().toString()));
        }else if (genericRabbitMQMessage.getEndpoint().equals("api/v1/content/update-reply-votes")) {
            GenericResponse genericResponse = objectMapper.convertValue(genericRabbitMQMessage.getMessage(), GenericResponse.class);
            replyService.updateVoteCount(genericResponse);
        }
    }
}
