package org.cripsy.authenticationservice.controller;

import org.cripsy.authenticationservice.dto.AuthDTO;
import org.cripsy.authenticationservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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