package org.yigitcanyontem.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(
        basePackageClasses = {
                org.yigitcanyontem.clients.users.UsersClient.class,
                org.yigitcanyontem.clients.notification.NotificationClient.class,
                org.yigitcanyontem.clients.auth.AuthClient.class,
                org.yigitcanyontem.clients.cache.CacheClient.class,
                org.yigitcanyontem.clients.content.ContentClient.class,
                org.yigitcanyontem.clients.report.ReportClient.class
        }
)
@SpringBootApplication(
        scanBasePackages = {
                "org.yigitcanyontem.cache",
                "org.yigitcanyontem.amqp"
        }
)
@EnableCaching
public class CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }

}
