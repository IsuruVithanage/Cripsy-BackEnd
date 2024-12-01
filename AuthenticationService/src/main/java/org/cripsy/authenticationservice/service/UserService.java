package org.cripsy.authenticationservice.service;

import org.cripsy.authenticationservice.dto.UsersDTO;
import org.cripsy.authenticationservice.model.Users;
import org.cripsy.authenticationservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    @Value("${admin.service.url}")
    private String adminServiceUrl;

    @Value("${delivery.service.url}")
    private String deliveryServiceUrl;

    public Users createUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public String verifyUser(Users user) {
//        Authentication auth =
//                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//        if (auth.isAuthenticated()) {
//            return jwtService.generateToken(user.getUsername());
//        }
//
//        return "Invalid username or password";

        if (checkUserInExternalService(customerServiceUrl, user) ||
                checkUserInExternalService(adminServiceUrl, user) ||
                checkUserInExternalService(deliveryServiceUrl, user)) {
            return jwtService.generateToken(user.getUsername());
        }

        throw new RuntimeException("Invalid username or password");
    }

    private boolean checkUserInExternalService(String serviceUrl, Users user) {
        try {
            // Make an API call to the external service
            ResponseEntity<UsersDTO> response = restTemplate.postForEntity(
                    serviceUrl + "/auth/check-user",
                    user,
                    UsersDTO.class
            );

            UsersDTO externalUser = response.getBody();
            return externalUser != null &&
                    passwordEncoder.matches(user.getPassword(), externalUser.getPassword());
        } catch (Exception e) {
            // Handle errors gracefully (e.g., service unavailable)
            return false;
        }
    }
}
