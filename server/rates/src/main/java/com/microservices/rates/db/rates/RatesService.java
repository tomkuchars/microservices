package com.microservices.rates.db.rates;

import java.time.LocalDate;

import org.reactivestreams.Publisher;

import com.microservices.rates.db.rates.model.Rates;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatesService {

  Mono<Rates> getRatesById(Integer id);

  Mono<Rates> getRatesByBaseAndCurrencyAndDate(String base, String currency, LocalDate date);

  Mono<Rates> getRatesByCurrencyAndDate(String currency, LocalDate date);

  Flux<Rates> getRatesListByDate(LocalDate date);

  Mono<Rates> save(Rates rates);

  Flux<Rates> saveAll(Publisher<Rates> ratesList);
}
