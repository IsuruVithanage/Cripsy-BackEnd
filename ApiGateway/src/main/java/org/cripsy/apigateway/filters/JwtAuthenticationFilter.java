package org.cripsy.apigateway.filters;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<Object> {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            logger.info("Entering JwtAuthenticationFilter");

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.warn("Authorization header is missing");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                logger.info("Token extracted successfully: {}", token);
            } else {
                logger.warn("Invalid token format");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
                logger.info("Token validated successfully, claims: {}", claims);

                exchange.getAttributes().put("claims", claims);
            } catch (Exception e) {
                logger.error("Token validation failed: {}", e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}
