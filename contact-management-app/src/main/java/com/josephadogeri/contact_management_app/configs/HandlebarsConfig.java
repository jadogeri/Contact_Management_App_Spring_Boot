package com.josephadogeri.contact_management_app.configs;

import com.github.jknack.handlebars.Handlebars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlebarsConfig {

    @Bean
    public Handlebars handlebars() {
        // Configure and return your Handlebars instance
        return new Handlebars();
    }
}