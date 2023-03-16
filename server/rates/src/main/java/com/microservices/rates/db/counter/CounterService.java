package com.microservices.rates.db.counter;

import java.time.LocalDate;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.rates.db.counter.model.Counter;

import reactor.core.publisher.Mono;

@Transactional
public interface CounterService {

  Mono<Counter> getCounterByCurrencyAndDate(String currency, LocalDate date);

  Mono<Void> createAndIncrementCounter(String currency, LocalDate date);

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  Mono<Void> createCounter(String currency, LocalDate date);

  Mono<Void> incrementCounter(String currency, LocalDate date);

}
