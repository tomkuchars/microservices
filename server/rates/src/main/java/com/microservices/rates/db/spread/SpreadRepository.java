package com.microservices.rates.db.spread;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.microservices.rates.db.spread.model.Spread;

import reactor.core.publisher.Mono;

public interface SpreadRepository extends ReactiveCrudRepository<Spread, String> {

  Mono<Spread> findByCurrency(String currency);

}