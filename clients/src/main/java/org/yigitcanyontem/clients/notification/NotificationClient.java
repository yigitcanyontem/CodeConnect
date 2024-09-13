package org.yigitcanyontem.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.yigitcanyontem.clients.FeignConfiguration;

@FeignClient(
        name = "notification",
        configuration = FeignConfiguration.class
)
public interface NotificationClient {
    @GetMapping(path = "api/v1/notification/{notificationId}")
    NotificationDto getNotification(@PathVariable("notificationId") Integer notificationId);

    @PostMapping("api/v1/notification")
    NotificationDto sendNotification(@RequestBody NotificationCreateDto notificationCreateDto);

}
