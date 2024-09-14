package org.yigitcanyontem.cache.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.yigitcanyontem.cache.service.CacheService;
import org.yigitcanyontem.clients.users.dto.UsersDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheConsumer {

    private final CacheService cacheService;

    @RabbitListener(queues = "${rabbitmq.queues.user-cache}")
    public void saveOrUpdateUserConsumer(UsersDto usersDto) {
        log.info("Consuming message: {}", usersDto);
        cacheService.saveOrUpdateUser(usersDto);
    }
}
