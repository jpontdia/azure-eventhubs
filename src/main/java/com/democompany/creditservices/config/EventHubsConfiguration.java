package com.democompany.creditservices.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class EventHubsConfiguration {

    @Bean
    Consumer<String> produce() {
        return message -> {
            log.debug("Message sent to eventHubs: {}", message);
        };
    }


}
