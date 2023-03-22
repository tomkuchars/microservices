package com.microservices.rates;

import static org.mockito.ArgumentMatchers.eq;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.microservices.rates.db.counter.CounterService;
import com.microservices.rates.db.rates.RatesService;
import com.microservices.rates.db.rates.model.Rates;
import com.microservices.rates.db.spread.SpreadService;
import com.microservices.rates.db.spread.model.Spread;
import com.microservices.rates.rest.model.ExchangeResponse;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = { ExchangeRates.class })
public class ExchangeRatesTests {

  @Autowired
  private ExchangeRates exchangeRates;

  @MockBean
  private RatesService ratesService;

  @MockBean
  private SpreadService spreadService;

  @MockBean
  private CounterService counterService;

  private static final Optional<LocalDate> NOW = Optional.of(LocalDate.now());

  private interface Currencies {
    Optional<String> USD = Optional.of("USD");
    Optional<String> EUR = Optional.of("EUR");
    Optional<String> PLN = Optional.of("PLN");
  }

  @Test
  void givenRatesAndSpread_whenExchange_thenCalculateAndReturn() {
    Rates eurRates = new Rates(1, Currencies.USD.get(), Currencies.EUR.get(), NOW.get(), BigDecimal.valueOf(0.8));
    Rates plnRates = new Rates(2, Currencies.USD.get(), Currencies.PLN.get(), NOW.get(), BigDecimal.valueOf(3.7));
    Mockito.when(ratesService.getRatesByCurrencyAndDate(eq(Currencies.EUR), eq(NOW))).thenReturn(Mono.just(eurRates));
    Mockito.when(ratesService.getRatesByCurrencyAndDate(eq(Currencies.PLN), eq(NOW))).thenReturn(Mono.just(plnRates));

    Spread eurSpread = new Spread(Currencies.EUR.get(), BigDecimal.valueOf(0.01));
    Spread plnSpread = new Spread(Currencies.PLN.get(), BigDecimal.valueOf(0.04));
    Mockito.when(spreadService.getSpreadByCurrency(eq(Currencies.EUR.get()))).thenReturn(Mono.just(eurSpread));
    Mockito.when(spreadService.getSpreadByCurrency(eq(Currencies.PLN.get()))).thenReturn(Mono.just(plnSpread));

    Mockito.when(counterService.incrementCounter(eq(Currencies.EUR.get()), eq(NOW.get())))
        .thenReturn(Mono.just(Boolean.TRUE));
    Mockito.when(counterService.incrementCounter(eq(Currencies.PLN.get()), eq(NOW.get())))
        .thenReturn(Mono.just(Boolean.TRUE));

    Mono<ExchangeResponse> exchange = exchangeRates.exchange(Currencies.EUR, Currencies.PLN, NOW);
    StepVerifier
        .create(exchange)
        .expectNext(new ExchangeResponse(Currencies.EUR.get(), Currencies.PLN.get(), BigDecimal.valueOf(4.44)))
        .expectComplete()
        .verify();
  }

}
