package com.microservices.rates.db.counter.model;

import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Counter {

  @Id
  private Integer id;
  private String currency;
  private LocalDate date;
  private BigInteger counter;

}