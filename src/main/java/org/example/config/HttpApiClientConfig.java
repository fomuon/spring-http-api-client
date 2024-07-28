package org.example.config;

import org.example.httpclient.EnableRestTemplateApiClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRestTemplateApiClients(
        basePackages = "org.example.controller"
)
public class HttpApiClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
