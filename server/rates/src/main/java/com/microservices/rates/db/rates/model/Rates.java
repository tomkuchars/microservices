package com.microservices.rates.db.rates.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rates {

  @Id
  private Integer id;
  private String base;
  private String currency;
  private LocalDate date;
  private BigDecimal rate;

}