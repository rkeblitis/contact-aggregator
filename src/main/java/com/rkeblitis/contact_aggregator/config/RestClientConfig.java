package com.rkeblitis.contact_aggregator.config;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient kenectRestClient(KenectLabsProperties properties) {
       SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
       requestFactory.setConnectTimeout(Duration.ofSeconds(3));
       requestFactory.setReadTimeout(Duration.ofSeconds(5));

        return RestClient.builder()
            .requestFactory(requestFactory)
            .baseUrl(properties.baseUrl())
            .defaultHeader("Authorization", "Bearer " + properties.authToken())
            .build();
    }
}