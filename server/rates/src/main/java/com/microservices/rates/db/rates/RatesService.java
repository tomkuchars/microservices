package com.microservices.rates.db.rates;

import java.time.LocalDate;
import java.util.Optional;

import org.reactivestreams.Publisher;

import com.microservices.rates.db.rates.model.Rates;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RatesService {

  Mono<Rates> getRatesById(Integer id);

  Mono<Rates> getRatesByBaseAndCurrencyAndDate(String base, String currency, Optional<LocalDate> date);

  Mono<Rates> getRatesByCurrencyAndDate(Optional<String> currency, Optional<LocalDate> date);

  Flux<Rates> getRatesListByDate(LocalDate date);

  Mono<Rates> save(Rates rates);

  Flux<Rates> saveAll(Publisher<Rates> ratesList);
}
