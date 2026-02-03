package com.okved.finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@EnableScheduling
@EnableFeignClients(basePackages = "com.okved.finder.client")
@SpringBootApplication
public class OkvedFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkvedFinderApplication.class, args);
    }
}
