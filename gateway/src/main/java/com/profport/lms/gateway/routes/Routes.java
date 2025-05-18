package com.profport.lms.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {
    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.path("/users/**"), HandlerFunctions.http("http://user:8081")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> coursesServiceRoute() {
        return GatewayRouterFunctions.route("courses_service")
                .route(RequestPredicates.path("/courses/**"), HandlerFunctions.http("http://course:8082")).build();
    }
}
