package org.cripsy.authenticationservice.service;

import org.cripsy.authenticationservice.dto.UsersDTO;
import org.cripsy.authenticationservice.model.Users;
import org.cripsy.authenticationservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository; // Local Users repository

    @Autowired
    private RestTemplate restTemplate;

    @Value("${admin.service.url}")
    private String adminServiceUrl;

    @Value("${delivery.service.url}")
    private String deliveryServiceUrl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First, check if the user exists in the local Users database (Auth Service)
        UsersDTO user = findUserInLocalDatabase(username);

        // If not found in local DB, check external services
        if (user == null) {
            user = checkUserInExternalService(adminServiceUrl, username);
        }
        if (user == null) {
            user = checkUserInExternalService(deliveryServiceUrl, username);
        }

        // If user is still not found, throw UsernameNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Build UserDetails object for Spring Security
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }


    private UsersDTO findUserInLocalDatabase(String username) {
        Users foundUser = usersRepository.findUserByUsername(username);
        if (foundUser != null) {
            UsersDTO userDTO = new UsersDTO();
            userDTO.setUsername(foundUser.getUsername());
            userDTO.setPassword(foundUser.getPassword());
            return userDTO;
        }
        return null;
    }


    private UsersDTO checkUserInExternalService(String serviceUrl, String username) {
        try {
            return restTemplate.getForObject(serviceUrl + "/auth/check-user/" + username, UsersDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
