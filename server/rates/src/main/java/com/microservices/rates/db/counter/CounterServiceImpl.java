package com.microservices.rates.db.counter;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.microservices.rates.db.counter.model.Counter;

import reactor.core.publisher.Mono;

@Service
public class CounterServiceImpl implements CounterService {

  private final CounterRepository counterRepository;

  public CounterServiceImpl(CounterRepository counterRepository) {
    this.counterRepository = counterRepository;
  }

  @Lazy
  @Autowired
  private CounterService counterService;

  @Override
  public Mono<Counter> getCounterByCurrencyAndDate(String currency, LocalDate date) {
    return counterRepository.findByCurrencyAndDate(currency, date);
  }

  @Override
  public Mono<Void> createAndIncrementCounter(String currency, LocalDate date) {
    return counterService.createCounter(currency, date).then(counterService.incrementCounter(currency, date));
  }

  @Override
  public Mono<Void> createCounter(String currency, LocalDate date) {
    return counterRepository.createCounter(currency, date);
  }

  @Override
  public Mono<Void> incrementCounter(String currency, LocalDate date) {
    return counterRepository.incrementCounter(currency, date);
  }
}
