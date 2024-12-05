package org.cripsy.authenticationservice.service;

import org.cripsy.customerservice.dto.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;


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