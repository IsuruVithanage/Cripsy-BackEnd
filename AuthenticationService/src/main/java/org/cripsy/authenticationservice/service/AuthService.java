package org.cripsy.authenticationservice.service;

import org.cripsy.authenticationservice.dto.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    private final WebClient webClient;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    @Value("${admin.service.url}")
    private String adminServiceUrl;

    @Value("${delivery.service.url}")
    private String deliveryServiceUrl;


    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(customerServiceUrl).build();;
    }

    public String createUser(AuthDTO authDTO) {
        authDTO.setPassword(passwordEncoder.encode(authDTO.getPassword()));

        try {
            authDTO.setPassword(passwordEncoder.encode(authDTO.getPassword()));

            return webClient.post()
                    .uri( "http://localhost:8081/api/customers/signup")
                    .bodyValue(authDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return "Failed to register customer: " + e.getMessage();
        }
    }



}