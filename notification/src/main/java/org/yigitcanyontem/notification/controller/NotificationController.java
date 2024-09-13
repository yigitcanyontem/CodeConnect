package org.yigitcanyontem.notification.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.yigitcanyontem.clients.notification.NotificationCreateDto;
import org.yigitcanyontem.clients.notification.NotificationDto;
import org.yigitcanyontem.notification.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/notification")
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(path = "{notificationId}")
    public NotificationDto getNotification(@PathVariable("notificationId") Integer notificationId) {
        return notificationService.getNotification(notificationId);
    }


    @PostMapping()
    public NotificationDto sendNotification(@RequestBody NotificationCreateDto notificationCreateDto) {
        return notificationService.sendNotification(notificationCreateDto);
    }

}
