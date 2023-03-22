package com.microservices.rates.db.counter;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.microservices.rates.db.counter.model.Counter;

import reactor.core.publisher.Mono;

@Service
public class CounterServiceImpl implements CounterService {

  private final CounterRepository counterRepository;

  public CounterServiceImpl(CounterRepository counterRepository) {
    this.counterRepository = counterRepository;
  }

  @Override
  public Mono<Counter> getCounterByCurrencyAndDate(String currency, LocalDate date) {
    return counterRepository.findByCurrencyAndDate(currency, date);
  }

  @Override
  public Mono<Boolean> incrementCounter(String currency, LocalDate date) {
    return counterRepository.incrementCounter(currency, date);
  }
}
