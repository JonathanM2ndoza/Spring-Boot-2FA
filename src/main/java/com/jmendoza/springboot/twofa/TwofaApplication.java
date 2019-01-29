package com.jmendoza.springboot.twofa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TwofaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwofaApplication.class, args);
    }

}

