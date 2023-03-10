package com.microservices.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class SettingsRouter {

  @Bean
  RouterFunction<ServerResponse> assetsRoute() {
    return RouterFunctions
        .resources("/assets/**", new ClassPathResource("assets/"));
  }

  // @Bean
  // RouterFunction<ServerResponse> route() {
  //   return RouterFunctions
  //       .route(RequestPredicates.GET("/settings"), request -> ServerResponse
  //           .ok()
  //           .build());
  // }

}
