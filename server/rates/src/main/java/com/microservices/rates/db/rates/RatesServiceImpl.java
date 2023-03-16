package com.microservices.rates.db.rates;

import java.time.LocalDate;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.rates.RatesProperties;
import com.microservices.rates.db.rates.model.Rates;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class RatesServiceImpl implements RatesService {

  private final RatesRepository ratesRepository;

  private final RatesProperties ratesProperties;

  public RatesServiceImpl(RatesRepository ratesRepository, RatesProperties ratesProperties) {
    this.ratesRepository = ratesRepository;
    this.ratesProperties = ratesProperties;
  }

  @Override
  public Mono<Rates> getRatesById(Integer id) {
    return ratesRepository.findById(id);
  }

  @Override
  public Mono<Rates> getRatesByBaseAndCurrencyAndDate(String base, String currency, LocalDate date) {
    return date != null ? ratesRepository.findTopByBaseAndCurrencyAndDateOrderByIdDesc(base, currency, date)
        : ratesRepository.findTopByBaseAndCurrencyOrderByDateDescIdDesc(base, currency);
  }

  @Override
  public Mono<Rates> getRatesByCurrencyAndDate(String currency, LocalDate date) {
    return getRatesByBaseAndCurrencyAndDate(ratesProperties.baseCurrency(), currency, date);
  }

  @Override
  public Flux<Rates> getRatesListByDate(LocalDate date) {
    return ratesRepository.findByDate(date);
  }

  @Override
  public Mono<Rates> save(Rates rates) {
    return ratesRepository.save(rates);
  }

  @Override
  public Flux<Rates> saveAll(Publisher<Rates> ratesList) {
    return ratesRepository.saveAll(ratesList);
  }

}
