package com.rkeblitis.contact_aggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix= "kenect.labs")
public record KenectLabsProperties(String baseUrl, String authToken) {}