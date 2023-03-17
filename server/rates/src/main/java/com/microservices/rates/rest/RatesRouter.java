package com.microservices.rates.rest;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RatesRouter {

  @Bean
  RouterFunction<ServerResponse> route(RatesHandler exchangeHandler) {
    return RouterFunctions
        .route(GET("/exchange"), exchangeHandler::exchange)
        .andRoute(PUT("/load"), exchangeHandler::load);
  }
}
