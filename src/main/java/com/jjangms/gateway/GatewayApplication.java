package com.jjangms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Bean
  RouteLocator gateway(RouteLocatorBuilder routeLocatorBuilder) {
    return routeLocatorBuilder
        .routes()
        .route(routeSpect -> routeSpect
            .path("/hello")
            .and()
            .host("*.spring.io")
            .filters(gatewayFilterSpec ->
                gatewayFilterSpec.setPath("/guides"))
            .uri("https://spring.io/")
        )
        .route("twitter", routeSpect -> routeSpect
            .path("/twitter/@**")
            .filters(filters -> filters.rewritePath(
                "/twiiter/(?<handle>.*)", "/${handle}"
            ))
            .uri("https://twitter.com/@")
        )
        .build();
  }
}
