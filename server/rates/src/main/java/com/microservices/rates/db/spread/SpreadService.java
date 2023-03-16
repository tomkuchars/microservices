package com.microservices.rates.db.spread;

import com.microservices.rates.db.spread.model.Spread;

import reactor.core.publisher.Mono;

public interface SpreadService {

  Mono<Spread> getSpreadByCurrency(String currency);

}
