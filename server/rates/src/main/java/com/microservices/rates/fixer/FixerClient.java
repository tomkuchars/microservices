package com.microservices.rates.fixer;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.rates.RatesProperties;
import com.microservices.rates.fixer.model.LatestResponse;

import reactor.core.publisher.Mono;

@Component
public class FixerClient {

  private final FixerProperties fixerProperties;
  private final RatesProperties ratesProperties;

  private final WebClient webClient;

  public FixerClient(FixerProperties fixerProperties, RatesProperties ratesProperties) {
    this.fixerProperties = fixerProperties;
    this.ratesProperties = ratesProperties;
    this.webClient = WebClient.builder()
        .baseUrl(this.fixerProperties.apiUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("apikey", this.fixerProperties.apiKey())
        .build();
  }

  public Mono<LatestResponse> getLatest() {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/latest")
            .queryParam("base", ratesProperties.baseCurrency())
            .build())
        .retrieve()
        .toEntity(LatestResponse.class)
        .map(ResponseEntity::getBody);
  }
}
