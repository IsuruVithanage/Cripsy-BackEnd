package org.cripsy.authenticationservice.controller;

import org.apache.http.auth.InvalidCredentialsException;
import org.cripsy.customerservice.dto.AuthDTO;
import org.cripsy.authenticationservice.service.AuthService;
import org.cripsy.customerservice.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody AuthDTO authDTO) {
        return authService.createUser(authDTO);
    }

    @PostMapping("/login")
    public CustomerDTO login(@RequestBody AuthDTO authDTO) throws InvalidCredentialsException {
        return authService.findUser(authDTO.getUsername(), authDTO.getPassword());
    }
}
