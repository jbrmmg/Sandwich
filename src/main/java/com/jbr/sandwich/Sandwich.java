package com.jbr.sandwich;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import com.jbr.sandwich.config.ApplicationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class Sandwich {
    private static final Logger log = LoggerFactory.getLogger(Sandwich.class);

    public static void main(String[] args) {
        log.info("Starting up.");
        SpringApplication app = new SpringApplication(Sandwich.class);
        app.run();
    }
}
