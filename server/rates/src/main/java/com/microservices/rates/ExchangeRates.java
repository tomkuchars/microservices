package com.microservices.rates;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.microservices.rates.db.counter.CounterService;
import com.microservices.rates.db.rates.RatesService;
import com.microservices.rates.db.spread.SpreadService;
import com.microservices.rates.rest.model.ExchangeResponse;

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
  private final LoadRates loadRates;

  public ExchangeRates(RatesService ratesService, SpreadService spreadService, CounterService counterService,
      LoadRates loadRates) {
    this.ratesService = ratesService;
    this.spreadService = spreadService;
    this.counterService = counterService;
    this.loadRates = loadRates;
  }

  public Mono<ExchangeResponse> exchange(String from, String to, LocalDate date) { //TODO
    return this.counterService.createAndIncrementCounter(from, date)
        .map((Void v) -> new ExchangeResponse(from, to, BigDecimal.TEN));
  }

  // @GetMapping("/exchange")
  // public ResponseEntity<ExchangeResponse> exchange(@RequestParam(value =
  // EXCHANGE_PARAM_FROM) String from,
  // @RequestParam(value = EXCHANGE_PARAM_TO) String to,
  // @RequestParam(value = EXCHANGE_PARAM_DATE, required = false)
  // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
  // Rates fromRates = getRatesAndIncrement(from, EXCHANGE_PARAM_FROM, date);
  // Rates toRates = getRatesAndIncrement(to, EXCHANGE_PARAM_TO, date);
  // Spread fromSpread = spreadService.getSpreadByCurrency(from);
  // Spread toSpread = spreadService.getSpreadByCurrency(to);
  // Spread spread = fromSpread.spread.compareTo(toSpread.spread) > 0 ? fromSpread
  // : toSpread;
  // BigDecimal exchange = toRates.rate.divide(fromRates.rate, EXCHANGE_SCALE,
  // RoundingMode.HALF_UP)
  // .multiply(BigDecimal.ONE.subtract(spread.spread)).stripTrailingZeros();
  // ExchangeResponse exchangeResponse = new ExchangeResponse(from, to, exchange);
  // return ResponseEntity.ok(exchangeResponse);
  // }

  // private Rates getRatesAndIncrement(String paramValue, String paramName,
  // LocalDate date)
  // throws ResponseStatusException {
  // Rates rates = ratesService.getRatesByCurrencyAndDate(paramValue, date);
  // if (rates == null) {
  // counterService.incrementCounterByCurrencyAndDate(paramValue, date);
  // throw new ResponseStatusException(HttpStatus.NOT_FOUND,
  // "Rates not found for " + paramName + "=" + paramValue);
  // } else {
  // counterService.incrementCounterByCurrencyAndDate(paramValue, rates.date);
  // }
  // return rates;
  // }

  // @PutMapping("/exchange")
  // public ResponseEntity<Integer> exchange() {
  // int size = loadRates.load();
  // return ResponseEntity.ok(size);
  // }

}
