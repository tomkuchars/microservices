package com.microservices.rates.db.counter;

import java.time.LocalDate;

import com.microservices.rates.db.counter.model.Counter;

import reactor.core.publisher.Mono;

public interface CounterService {

  Mono<Counter> getCounterByCurrencyAndDate(String currency, LocalDate date);

  Mono<Boolean> incrementCounter(String currency, LocalDate date);

}
