package org.cripsy.authenticationservice.service;

import org.cripsy.authenticationservice.dto.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    @Value("${admin.service.url}")
    private String adminServiceUrl;

    @Value("${delivery.service.url}")
    private String deliveryServiceUrl;


    public ResponseEntity<String> createUser(AuthDTO authDTO) {
        authDTO.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        String url = customerServiceUrl + "/customers";

        try {
            HttpEntity<AuthDTO> entity = new HttpEntity<>(authDTO);
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Void.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to create user in Customer Service");
            }

            return ResponseEntity.ok("User created successfully");
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body("Error occurred while communicating with Customer Service: " + e.getMessage());
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
