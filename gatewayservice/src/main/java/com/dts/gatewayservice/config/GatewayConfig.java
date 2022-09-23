//package com.dts.gatewayservice.config;
//import com.dts.gatewayservice.filter.JwtAuthenticationFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayConfig {
//
//    @Autowired
//    private JwtAuthenticationFilter filter;
//
//    @Bean
//    public RouteLocator configureRoute(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("user-service", r -> r.path("/api/user/**").filters(f -> f.filter(filter))
//                .uri("http://localhost:8081"))
//                .route("cart-service", r -> r.path("/api/cart/**").filters(f -> f.filter(filter))
//                .uri("http://localhost:8082"))
//                .build();
//    }
//
//
//}
