package com.microservices.rates;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.microservices.rates.db.counter.CounterService;
import com.microservices.rates.db.rates.RatesService;
import com.microservices.rates.db.spread.SpreadService;
import com.microservices.rates.rest.model.ExchangeResponse;
import com.microservices.rates.rest.model.RatesNotFound;

import reactor.core.publisher.Mono;

@Service
public class ExchangeRates {

  public static final String EXCHANGE_PARAM_FROM = "from";
  public static final String EXCHANGE_PARAM_TO = "to";
  public static final String EXCHANGE_PARAM_DATE = "date";
  public static final int EXCHANGE_SCALE = 30;
  private final RatesService ratesService;
  private final SpreadService spreadService;
  private final CounterService counterService;

  public ExchangeRates(RatesService ratesService, SpreadService spreadService, CounterService counterService) {
    this.ratesService = ratesService;
    this.spreadService = spreadService;
    this.counterService = counterService;
  }

  record RatesWithSpread(BigDecimal rate, String currency, BigDecimal spread) {
  }

  public Mono<ExchangeResponse> exchange(Optional<String> from, Optional<String> to, Optional<LocalDate> date) {
    return getRatesWithSpread(from, date)
        .zipWith(getRatesWithSpread(to, date))
        .map(tuple -> new ExchangeResponse(
            tuple.getT1().currency(),
            tuple.getT2().currency(),
            calculateExchange(tuple.getT1(), tuple.getT2())));
  }

  private Mono<RatesWithSpread> getRatesWithSpread(Optional<String> currency, Optional<LocalDate> date) {
    return ratesService.getRatesByCurrencyAndDate(currency, date)
        .flatMap(rates -> counterService.incrementCounter(rates.getCurrency(), rates.getDate())
            .filter(Boolean.TRUE::equals)
            .map(incremented -> rates))
        .switchIfEmpty(Mono.error(new RatesNotFound()))
        .flatMap(rates -> spreadService.getSpreadByCurrency(rates.getCurrency())
            .map(spread -> new RatesWithSpread(rates.getRate(), rates.getCurrency(), spread.getSpread())));
  }

  private BigDecimal calculateExchange(RatesWithSpread from, RatesWithSpread to) {
    return to.rate().divide(from.rate(), EXCHANGE_SCALE, RoundingMode.HALF_UP)
        .multiply(BigDecimal.ONE.subtract(from.spread().max(to.spread())))
        .stripTrailingZeros();
  }

}
