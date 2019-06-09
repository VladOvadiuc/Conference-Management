package ro.ubb.catalog.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ubb.catalog.core.model.Conference;

@Configuration
public class ConferenceConfig {
    @Bean
    public Conference conference() {
        return new Conference();
    }
}
