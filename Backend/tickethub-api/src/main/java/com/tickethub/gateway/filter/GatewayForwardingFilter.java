package com.tickethub.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GatewayForwardingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    Authentication auth = context.getAuthentication();
                    if (auth != null) {
                        ServerHttpRequest request = exchange.getRequest().mutate()
                                .header("X-Auth-Username", auth.getName())
                                .header("X-Auth-Roles", auth.getAuthorities().stream()
                                        .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                                        .collect(java.util.stream.Collectors.joining(",")))
                                .build();
                        return exchange.mutate().request(request).build();
                    }
                    return exchange;
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1; // chạy sau auth để lấy được context
    }
}
