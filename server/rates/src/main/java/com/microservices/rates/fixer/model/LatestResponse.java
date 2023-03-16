package com.microservices.rates.fixer.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record LatestResponse(
    String base,
    LocalDate date,
    Map<String, BigDecimal> rates,
    Boolean success,
    Long timestamp) {
}
