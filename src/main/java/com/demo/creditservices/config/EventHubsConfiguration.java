package com.demo.creditservices.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@Slf4j
@SuppressWarnings("unused")
public class EventHubsConfiguration {

    @Bean
    public Sinks.Many<String> many() {
        return Sinks.many().unicast().onBackpressureBuffer();

    }

    @Bean
    public Supplier<Flux<String>> supply(Sinks.Many<String> many) {
        return () -> many.asFlux()
                .doOnNext(m -> log.info("Message sent: {}", m))
                .doOnError(t -> log.error("Error encountered while sending message", t));
    }

    @Bean
    public Consumer<String> consume() {
        return message -> {
            log.info("New message received: '{}'", message);
        };
    }
}
