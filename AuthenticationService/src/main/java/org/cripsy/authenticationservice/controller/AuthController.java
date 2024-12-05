package org.cripsy.authenticationservice.controller;

import org.cripsy.authenticationservice.service.AuthService;
import org.cripsy.customerservice.dto.AuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public String signup(@RequestBody AuthDTO authDTO) {
        return authService.createUser(authDTO);
    }

}