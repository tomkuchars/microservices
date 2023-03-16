package com.microservices.rates.rest.model;

import java.math.BigDecimal;

public record ExchangeResponse(String from, String to, BigDecimal exchange) {
}
