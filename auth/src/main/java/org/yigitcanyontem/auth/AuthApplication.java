package org.yigitcanyontem.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        basePackageClasses = {
                org.yigitcanyontem.clients.users.UsersClient.class,
                org.yigitcanyontem.clients.notification.NotificationClient.class,
                org.yigitcanyontem.clients.auth.AuthClient.class,
                org.yigitcanyontem.clients.cache.CacheClient.class,
                org.yigitcanyontem.clients.content.ContentClient.class
        }
)
@SpringBootApplication(
        scanBasePackages = {
                "org.yigitcanyontem.auth",
                "org.yigitcanyontem.amqp"
        }
)
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
