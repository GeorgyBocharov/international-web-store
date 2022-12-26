package ru.sbt.store.read.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ru.sbt.store.read.api.loader.CacheLoaderTask;

import java.util.List;

@SpringBootApplication(scanBasePackages = "ru.sbt.store.read.api")
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class ReadApiApplication implements CommandLineRunner {

    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private List<CacheLoaderTask> cacheLoaderTasks;

    public static void main(String[] args) {
        SpringApplication.run(ReadApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        cacheLoaderTasks.forEach(task -> threadPoolTaskScheduler.schedule(task, task.getPeriodicTrigger()));
    }

    @Autowired
    public void setThreadPoolTaskScheduler(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @Autowired
    public void setCacheLoaderTasks(List<CacheLoaderTask> cacheLoaderTasks) {
        this.cacheLoaderTasks = cacheLoaderTasks;
    }
}
