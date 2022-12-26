package ru.sbt.store.compositor.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "ru.sbt.store.compositor.api")
@EnableDiscoveryClient
@EnableFeignClients
public class CompositorApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompositorApiApplication.class, args);
    }
}
