package com.microservices.rates.rest.model;

public class RatesNotFound extends Exception {

  public RatesNotFound() {
    super("Rates not found");
  }

}
