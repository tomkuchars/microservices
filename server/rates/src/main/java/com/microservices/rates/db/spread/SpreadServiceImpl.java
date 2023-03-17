package com.microservices.rates.db.spread;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.rates.RatesProperties;
import com.microservices.rates.db.spread.model.Spread;

import reactor.core.publisher.Mono;

@Service
@Transactional
public class SpreadServiceImpl implements SpreadService {

  private final SpreadRepository spreadRepository;
  private final RatesProperties ratesProperties;

  public SpreadServiceImpl(SpreadRepository spreadRepository, RatesProperties ratesProperties) {
    this.spreadRepository = spreadRepository;
    this.ratesProperties = ratesProperties;
  }

  @Override
  public Mono<Spread> getSpreadByCurrency(String currency) {
    return spreadRepository.findByCurrency(ratesProperties.baseCurrency().equals(currency) ? "BASE" : currency)
        .switchIfEmpty(spreadRepository.findByCurrency("ELSE"));
  }
}
