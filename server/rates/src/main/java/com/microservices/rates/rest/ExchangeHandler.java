package com.microservices.rates.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.microservices.rates.ExchangeRates;
import com.microservices.rates.LoadRates;
import com.microservices.rates.db.counter.CounterService;
import com.microservices.rates.rest.model.ExchangeResponse;

import reactor.core.publisher.Mono;

@Component
public class ExchangeHandler {

  public static final String EXCHANGE_PARAM_FROM = "from";
  public static final String EXCHANGE_PARAM_TO = "to";
  public static final String EXCHANGE_PARAM_DATE = "date";
  private final ExchangeRates exchange;
  private final LoadRates loadRates;

  public ExchangeHandler(ExchangeRates exchange, CounterService counterService,
      LoadRates loadRates) {
    this.exchange = exchange;
    this.loadRates = loadRates;
  }

  public Mono<ServerResponse> exchange(ServerRequest request) { //TODO
    return ServerResponse
        .ok()
        .body(this.exchange.exchange(
            request.queryParam(EXCHANGE_PARAM_FROM).orElse("USD"),
            request.queryParam(EXCHANGE_PARAM_TO).orElse("PLN"),
            request.queryParam(EXCHANGE_PARAM_DATE).map(date -> LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                .orElse(LocalDate.now())),
            ExchangeResponse.class);
  }

  public Mono<ServerResponse> load(ServerRequest request) {
    return ServerResponse.ok().body(loadRates.load().count(), Long.class);
  }

}
