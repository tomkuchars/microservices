package com.microservices.rates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.microservices.rates.fixer.FixerProperties;

import io.r2dbc.spi.ConnectionFactory;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ RatesProperties.class, FixerProperties.class })
public class RatesApplication {

  public static void main(String[] args) {
    SpringApplication.run(RatesApplication.class, args);
  }

  // @Bean
  // ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
  //   ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
  //   initializer.setConnectionFactory(connectionFactory);
  //   initializer.setDatabasePopulator(
  //       new ResourceDatabasePopulator(new ClassPathResource("schema.sql"), new ClassPathResource("data.sql")));
  //   return initializer;
  // }
}
