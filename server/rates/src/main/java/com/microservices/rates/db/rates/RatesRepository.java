package com.microservices.rates.db.rates;

import java.time.LocalDate;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.microservices.rates.db.rates.model.Rates;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatesRepository extends ReactiveCrudRepository<Rates, Integer> {

  Mono<Rates> findTopByBaseAndCurrencyAndDateOrderByIdDesc(String base, String currency, LocalDate date);

  Mono<Rates> findTopByBaseAndCurrencyOrderByDateDescIdDesc(String base, String currency);

  Flux<Rates> findByDate(LocalDate date);

}