package com.example.agence_immobiliere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AgenceImmobiliereApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgenceImmobiliereApplication.class, args);
    }

}
