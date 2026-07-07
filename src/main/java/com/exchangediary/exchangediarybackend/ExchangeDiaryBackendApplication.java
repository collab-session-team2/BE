package com.exchangediary.exchangediarybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ExchangeDiaryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeDiaryBackendApplication.class, args);
    }

}
