package com.gorevyonetimi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GorevYonetimiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GorevYonetimiApplication.class, args);
    }
}
