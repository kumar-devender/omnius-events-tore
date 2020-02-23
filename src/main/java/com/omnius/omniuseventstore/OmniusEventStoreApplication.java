package com.omnius.omniuseventstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OmniusEventStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(OmniusEventStoreApplication.class, args);
    }
}
