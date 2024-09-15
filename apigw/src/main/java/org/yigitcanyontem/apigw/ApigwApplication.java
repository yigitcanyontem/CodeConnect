package org.yigitcanyontem.apigw;

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
@SpringBootApplication
public class ApigwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigwApplication.class, args);
    }

}
