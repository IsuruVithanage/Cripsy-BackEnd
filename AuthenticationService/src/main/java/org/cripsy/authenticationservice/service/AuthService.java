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
    private RestTemplate restTemplate;

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


    public String login(String username, String password) {

        String token = authenticateUser(customerServiceUrl + "/login", username, password);
        if (token != null) return token;

        token = authenticateUser(adminServiceUrl + "/login", username, password);
        if (token != null) return token;

        token = authenticateUser(deliveryServiceUrl + "/login", username, password);
        if (token != null) return token;

        throw new RuntimeException("Invalid username or password");
    }

    private String authenticateUser(String url, String username, String password) {
        try {
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("username", username);
            requestBody.put("password", password);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            System.err.println("Error while authenticating with " + url + ": " + e.getMessage());
        }
        return null;
    }
}
