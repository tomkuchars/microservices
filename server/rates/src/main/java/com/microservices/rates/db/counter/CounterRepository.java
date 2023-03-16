package com.microservices.rates.db.counter;

import java.time.LocalDate;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.microservices.rates.db.counter.model.Counter;

import reactor.core.publisher.Mono;

public interface CounterRepository extends ReactiveCrudRepository<Counter, Integer> {

  Mono<Counter> findByCurrencyAndDate(String currency, LocalDate date);

  @Modifying
  @Query("insert into Counter (currency, date) values (:currency, :date) on conflict do nothing")
  Mono<Void> createCounter(@Param("currency") String currency, @Param("date") LocalDate date);

  @Modifying
  @Query("update Counter set counter = counter + 1 where currency=:currency and date=:date")
  Mono<Void> incrementCounter(@Param("currency") String currency, @Param("date") LocalDate date);

}