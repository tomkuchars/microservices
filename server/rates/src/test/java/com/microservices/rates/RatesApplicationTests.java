package com.microservices.rates;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RatesApplicationTests {

  @Autowired
  private ExchangeRates exchangeRates;

  @Test
  void contextLoads() {
    Assertions.assertNotNull(exchangeRates);
  }

}
