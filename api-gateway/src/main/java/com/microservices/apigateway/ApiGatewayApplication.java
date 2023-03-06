package com.microservices.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableDiscoveryClient
@Configuration
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

		return builder.routes()
				.route("ACCOUNT-SERVICE", r -> r.path(
						"/api/v1/account/**",
								"/api/v1/auth/**",
								"/api/v1/member/**",
								"/api/v1/user/**",
								"/api/v1/role/**",
								"/api/v1/earn-point/**",

							// swagger related path
								"/api/v1/account/swagger-ui/index.html"
							//	"/swagger-ui/**", "/swagger-resources/**" ,"/swagger-ui.html",
							//	"/v3/api-docs/**", "/webjars/**", "/view/**"
						)
						.uri("http://account-service:8080") ) // .uri("http://account-service:8080") )

				.route("MASTER-TABLE", r -> r.path("/master-table/**",
								"/api/v1/pick-list/**",

								// swagger related path
								"/master-table/swagger-ui/index.html"
								//"/swagger-ui/**", "/swagger-resources/**" ,"/swagger-ui.html",
								//"/v3/api-docs/**", "/webjars/**", "/view/**"
							)
						.uri("http://localhost:8081") )
				/*
				.route("CURRENCY-EXCHANGE", r -> r.path("/currency-exchange/**",
								"/currency-exchange/swagger-ui/index.html",
								"/swagger-ui/**", "/swagger-resources/**" ,"/swagger-ui.html",
								"/v3/api-docs/**", "/webjars/**", "/view/**")
						.uri("http://localhost:8000") )
				.route("CURRENCY-CONVERSION", r -> r.path("/currency-conversion/**",
								"/currency-conversion/swagger-ui/index.html",
								"/currency-conversion-feign/**",
								"/swagger-ui/**", "/-resources/**" ,"/swagger-ui.html",
								"/v3/api-docs/**", "/webjars/**", "/view/**")
						.filters(gatewayFilterSpec -> gatewayFilterSpec
								.rewritePath("/currency-conversion/test"
										, "/currency-conversion/swagger-ui/index.html"))
						.uri("http://localhost:8100")) //.uri("http://currency-conversion:8100") )
				 */

				.build();

	}
}
