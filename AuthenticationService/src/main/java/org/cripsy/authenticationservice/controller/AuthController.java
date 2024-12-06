package org.cripsy.authenticationservice.controller;

import org.apache.http.auth.InvalidCredentialsException;
import org.cripsy.authenticationservice.dto.AuthDTO;
import org.cripsy.authenticationservice.dto.LoginDTO;
import org.cripsy.authenticationservice.service.AuthService;
import org.cripsy.customerservice.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public void test(@RequestBody AuthDTO authDTO) {
        System.out.println("hgjhgjhg mhj");
    }

    @PostMapping("/login")
    public LoginDTO login(@RequestBody AuthDTO authDTO) throws InvalidCredentialsException {
        System.out.println("hjghjghjgjhgjhghjg");
        return authService.findUser(authDTO.getUsername(), authDTO.getPassword());
    }
}
