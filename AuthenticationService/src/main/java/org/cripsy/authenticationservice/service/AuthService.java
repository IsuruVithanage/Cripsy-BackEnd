package org.cripsy.authenticationservice.service;

import org.apache.http.auth.InvalidCredentialsException;
import org.cripsy.authenticationservice.dto.AuthDTO;
import org.cripsy.authenticationservice.dto.LoginDTO;
import org.cripsy.customerservice.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final WebClient webClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

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


//    public LoginDTO findUser(String username, String password) throws InvalidCredentialsException {
//        return validateUser(username, password);
//    }
        /*private LoginDTO validateUser(String username, String password) {
        // Create a request body containing the username
        Map<String, String> requestBody = Map.of("username", username);
//        System.out.println(password);
//        String decodedPassword = passwordEncoder.encode(requestBody.get("password"));
//        System.out.println(decodedPassword);


        try {
            // Send a POST request with the username in the body
            LoginDTO customer = webClient.post()
                    .uri("http://localhost:8081/api/customers/login")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            Mono.error(new InvalidCredentialsException("Invalid credentials or user not found")))
                    .bodyToMono(new ParameterizedTypeReference<LoginDTO>() {})
                    .block();

            System.out.println("User-entered password: " + password);
            System.out.println("Stored (encoded) password: " + customer.getPassword());
            if (!passwordEncoder.matches(password, customer.getPassword())) {
                throw new InvalidCredentialsException("Invalid credentials or user not found");
            }
            return customer;

        } catch (Exception e) {
            throw new RuntimeException("Failed to validate user: " + e.getMessage(), e);
        }
    }*/
    public LoginDTO findUser(String username, String password) {
        System.out.println(username);
        return validateUser(username,password);
    }

    private LoginDTO validateUser(String username, String password) {
        Map<String, String> requestBody = Map.of("username", username);

            LoginDTO user = webClient.post()
                    .uri("http://localhost:8081/api/customers/login")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<LoginDTO>() {})
                    .block();

        if (user != null) {
            user.setRole("Customer");
        }

        // If not found in CustomerService, search in DeliveryService
        if (user == null) {
            try {
                 user = webClient.post()
                        .uri("http://localhost:8087/api/delivery/login")
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<LoginDTO>() {})
                        .block();

                if (user != null) {
                    user.setRole("Delivery");
                }

            } catch (Exception ex) {
                throw new RuntimeException("User not found in both CustomerService and DeliveryService: " + username);
            }
        }


        // If not found in CustomerService, search in AdminService
        if (user == null) {
            try {
                 user = webClient.post()
                        .uri("http://localhost:8084/api/admin/login")
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<LoginDTO>() {})
                        .block();

                if (user != null) {
                    user.setRole("Admin");
                }

            } catch (Exception ex) {
                throw new RuntimeException("User not found in both CustomerService, Admin service and DeliveryService: " + username);
            }
        }

            if (user.getPassword() == null) {
                throw new RuntimeException("Password field is null for user: " + username);
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("misMatch");
            }


        String jwtToken = jwtService.generateJwtToken(user.getId(), username, user.getRole());
        System.out.println("Generated JWT: " + jwtToken);

        user.setToken(jwtToken);
        return user;
    }
//    private LoginDTO validateUser(String username, String password) throws InvalidCredentialsException {
//        // Define service URLs to check
//        String[] serviceUrls = {
//                customerServiceUrl + "/api/customers/login",
//                deliveryServiceUrl + "/api/delivery/login",
//                adminServiceUrl + "/api/admin/login"
//        };
//
//        Map<String, String> requestBody = Map.of("username", username);
//
//        // Attempt validation against each service
//        for (String serviceUrl : serviceUrls) {
//            LoginDTO user = tryValidateWithService(serviceUrl, requestBody, password);
//            if (user != null) {
//                return user; // Return as soon as a match is found
//            }
//        }
//
//        // If no service validated the user, throw exception
//        throw new InvalidCredentialsException("Invalid credentials or user not found in any service");
//    }
//
//    // Helper method to try validation with a single service
//    private LoginDTO tryValidateWithService(String serviceUrl, Map<String, String> requestBody, String rawPassword) {
//        try {
//            return webClient.post()
//                    .uri(serviceUrl)
//                    .bodyValue(requestBody)
//                    .retrieve()
//                    .onStatus(HttpStatusCode::isError, response ->
//                            Mono.error(new InvalidCredentialsException("Service validation failed: " + serviceUrl)))
//                    .bodyToMono(LoginDTO.class)
//                    .blockOptional()
//                    .filter(user -> user.getPassword() != null && passwordEncoder.matches(rawPassword, user.getPassword()))
//                    .orElse(null);
//        } catch (Exception e) {
//            System.out.println("Service validation failed for: " + serviceUrl + " - " + e.getMessage());
//            return null;
//        }
//    }


}