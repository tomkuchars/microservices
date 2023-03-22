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
  @Query("insert into Counter (currency, date, counter) values (:currency, :date, 1) on conflict (currency, date) do update set counter = Counter.counter + 1 where Counter.currency=:currency and Counter.date=:date")
  Mono<Boolean> incrementCounter(@Param("currency") String currency, @Param("date") LocalDate date);

}