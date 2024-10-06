package org.yigitcanyontem.user.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.yigitcanyontem.clients.shared.dto.GenericRabbitMQMessage;
import org.yigitcanyontem.clients.shared.dto.GenericResponse;
import org.yigitcanyontem.clients.users.dto.UserFollowDto;
import org.yigitcanyontem.user.service.UsersProfileService;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsersRabbitMQConsumer {

    private final ObjectMapper objectMapper;
    private final UsersProfileService usersProfileService;

    @RabbitListener(queues = "${rabbitmq.queues.user}")
    public void consumeUserQueue(GenericRabbitMQMessage genericRabbitMQMessage) {
        try {
            log.info("Consuming message: {}", genericRabbitMQMessage);
            if (genericRabbitMQMessage.getEndpoint().equals("api/v1/user-profile/update-following")) {
                UserFollowDto followDto = objectMapper.convertValue(genericRabbitMQMessage.getMessage(), UserFollowDto.class);
                usersProfileService.updateFollowCount(followDto);
            }
        }catch (Exception e) {
            log.error("Error while consuming message: {}", e.getMessage());
        }
    }
}
