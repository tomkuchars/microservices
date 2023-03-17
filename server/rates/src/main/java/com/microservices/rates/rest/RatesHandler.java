package com.microservices.rates.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.microservices.rates.ExchangeRates;
import com.microservices.rates.LoadRates;
import com.microservices.rates.rest.model.ExchangeResponse;
import com.microservices.rates.rest.model.LoadResponse;
import com.microservices.rates.rest.model.RatesNotFound;

import reactor.core.publisher.Mono;

@Component
public class RatesHandler {

  public static final String EXCHANGE_PARAM_FROM = "from";
  public static final String EXCHANGE_PARAM_TO = "to";
  public static final String EXCHANGE_PARAM_DATE = "date";
  private final ExchangeRates exchange;
  private final LoadRates loadRates;

  public RatesHandler(ExchangeRates exchange, LoadRates loadRates) {
    this.exchange = exchange;
    this.loadRates = loadRates;
  }

  public Mono<ServerResponse> exchange(ServerRequest request) {
    return this.exchange.exchange(
        request.queryParam(EXCHANGE_PARAM_FROM),
        request.queryParam(EXCHANGE_PARAM_TO),
        request.queryParam(EXCHANGE_PARAM_DATE).map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_DATE)))
        .flatMap(exchangeResponse -> ServerResponse.ok().body(Mono.just(exchangeResponse), ExchangeResponse.class))
        .onErrorResume(RatesNotFound.class, error -> ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> load(ServerRequest request) {
    return ServerResponse.ok().body(loadRates.load(), LoadResponse.class);
  }

}
