package com.dechev.newsletter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewsletterApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsletterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NewsletterApplication.class, args);

        LOGGER.error("Message logged at ERROR level");
        LOGGER.warn("Message logged at WARN level");
        LOGGER.info("Message logged at INFO level");
        LOGGER.debug("Message logged at DEBUG level");
    }

}
