package org.cripsy.authenticationservice.service;

import org.apache.http.auth.InvalidCredentialsException;
import org.cripsy.customerservice.dto.AuthDTO;
import org.cripsy.customerservice.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final WebClient webClient;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    @Value("${admin.service.url}")
    private String adminServiceUrl;

    @Value("${delivery.service.url}")
    private String deliveryServiceUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(customerServiceUrl).build();
    }

    public String createUser(AuthDTO authDTO) {
        authDTO.setPassword(passwordEncoder.encode(authDTO.getPassword()));

        try {
            return webClient.post()
                    .uri("http://localhost:8081/api/customers/signup")
                    .bodyValue(authDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            return "Failed to register customer: " + e.getMessage();
        }
    }

    public CustomerDTO findUser(String username, String password) throws InvalidCredentialsException {
        return validateUser(username, password);
    }

//    private CustomerDTO validateUser(String username, String password) {
//        // Create a request body containing the username
//        Map<String, String> requestBody = Map.of("username", username);
////        System.out.println(password);
////        String decodedPassword = passwordEncoder.encode(requestBody.get("password"));
////        System.out.println(decodedPassword);
//
//
//        try {
//            // Send a POST request with the username in the body
//            CustomerDTO customer = webClient.post()
//                    .uri("http://localhost:8081/api/customers/login")
//                    .bodyValue(requestBody)
//                    .retrieve()
//                    .onStatus(HttpStatusCode::isError, response ->
//                            Mono.error(new InvalidCredentialsException("Invalid credentials or user not found")))
//                    .bodyToMono(new ParameterizedTypeReference<CustomerDTO>() {})
//                    .block();
//
//            System.out.println("User-entered password: " + password);
//            System.out.println("Stored (encoded) password: " + customer.getPassword());
//            if (!passwordEncoder.matches(password, customer.getPassword())) {
//                throw new InvalidCredentialsException("Invalid credentials or user not found");
//            }
//            return customer;
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to validate user: " + e.getMessage(), e);
//        }
//    }

//    private CustomerDTO validateUser(String username, String password) {
//        Map<String, String> requestBody = Map.of("username", username);
//
//        try {
//            CustomerDTO customer = webClient.post()
//                    .uri("http://localhost:8081/api/customers/login")
//                    .bodyValue(requestBody)
//                    .retrieve()
//                    .onStatus(HttpStatusCode::isError, response ->
//                            Mono.error(new InvalidCredentialsException("Invalid credentials or user not found")))
//                    .bodyToMono(new ParameterizedTypeReference<CustomerDTO>() {})
//                    .block();
//
//            System.out.println("User-entered password: " + password);
//            System.out.println("Stored (encoded) password: " + customer.getPassword());
//
//            if (customer.getPassword() == null) {
//                throw new RuntimeException("Password field is null for user: " + username);
//            }
//
//            if (!passwordEncoder.matches(password, customer.getPassword())) {
//                throw new InvalidCredentialsException("Invalid credentials or user not found");
//            }
//
//            return customer;
//
//        } catch (Exception e) {
//            System.err.println("Failed to validate user: " + e.getMessage());
//            throw new RuntimeException("Failed to validate user: " + e.getMessage(), e);
//        }
//    }

    private CustomerDTO validateUser(String username, String password) throws InvalidCredentialsException {
        Map<String, String> requestBody = Map.of("username", username);

        // Attempt validation against Customer Service
        CustomerDTO customer = tryValidateWithService(customerServiceUrl + "/api/customers/login", requestBody, password);
        if (customer != null) return customer;

//        // Attempt validation against Delivery Service
//        CustomerDTO deliveryUser = tryValidateWithService(deliveryServiceUrl + "/api/delivery/login", requestBody, password);
//        if (deliveryUser != null) return deliveryUser;
//
//        // Attempt validation against Admin Service
//        CustomerDTO adminUser = tryValidateWithService(adminServiceUrl + "/api/admin/login", requestBody, password);
//        if (adminUser != null) return adminUser;

        // If none matched, throw an exception
        throw new InvalidCredentialsException("Invalid credentials or user not found in any service");
    }

    // Helper method to try validation with a single service
    private CustomerDTO tryValidateWithService(String serviceUrl, Map<String, String> requestBody, String rawPassword) {
        try {
            CustomerDTO user = webClient.post()
                    .uri(serviceUrl)
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, response ->
                            Mono.error(new InvalidCredentialsException("Invalid credentials or user not found in service: " + serviceUrl)))
                    .bodyToMono(new ParameterizedTypeReference<CustomerDTO>() {})
                    .block();


            if (user != null && user.getPassword() != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
                System.out.println("Password match result: true");
                return user;
            } else {
                System.out.println("Password mismatch for service: " + serviceUrl);
            }
        } catch (Exception e) {
            System.out.println("Validation failed for service: " + serviceUrl + " - " + e.getMessage());
        }
        return null;
    }


}
