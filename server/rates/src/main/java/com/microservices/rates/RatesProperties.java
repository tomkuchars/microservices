package com.microservices.rates;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rates")
public record RatesProperties(String baseCurrency) {
}
