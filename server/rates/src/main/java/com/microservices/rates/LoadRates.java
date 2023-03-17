package com.microservices.rates;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.microservices.rates.db.rates.RatesService;
import com.microservices.rates.db.rates.model.Rates;
import com.microservices.rates.fixer.FixerClient;
import com.microservices.rates.rest.model.LoadResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LoadRates {
  private final FixerClient fixerClient;

  private final RatesService ratesService;

  public LoadRates(FixerClient fixerClient, RatesService ratesService) {
    this.fixerClient = fixerClient;
    this.ratesService = ratesService;
  }

  private Flux<Rates> getAndSave() {
    return ratesService.saveAll(
        fixerClient.getLatest()
            .flatMapMany(latest -> Flux.fromIterable(latest.rates().entrySet())
                .map(entry -> new Rates(null, latest.base(), entry.getKey(), latest.date(), entry.getValue()))));
  }

  public Mono<Long> loadAndLog() {
    return getAndSave().count()
        .doOnSuccess(count -> System.out.println(LocalDateTime.now() + " Loaded rates: " + count));
  }

  public Mono<LoadResponse> load() {
    return loadAndLog().map(count -> new LoadResponse(count));
  }

  // @Scheduled(cron = "0 5 0 * * ?", zone = "UTC") //At 00:05:00am UTC every day
  // @Scheduled(cron = "0 30 9 * * ?", zone = "UTC") //At 09:30:00am UTC every day
  public void loadNow() {
    loadAndLog().block();
  }
}
