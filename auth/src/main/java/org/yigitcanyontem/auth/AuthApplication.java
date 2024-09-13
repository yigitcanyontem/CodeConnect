package org.yigitcanyontem.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        basePackageClasses = {
                org.yigitcanyontem.clients.users.UsersClient.class,
                org.yigitcanyontem.clients.notification.NotificationClient.class,
                org.yigitcanyontem.clients.auth.AuthClient.class
        }
)
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
