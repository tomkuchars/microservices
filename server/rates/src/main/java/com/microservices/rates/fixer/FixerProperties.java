package com.microservices.rates.fixer;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fixer")
public record FixerProperties(String apiUrl, String apiKey) {
}
