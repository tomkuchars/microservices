package com.microservices.rates.db.spread.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spread {

  @Id
  private String currency;
  private BigDecimal spread;

}