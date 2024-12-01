package org.cripsy.authenticationservice.controller;

import org.cripsy.authenticationservice.dto.UsersDTO;
import org.cripsy.authenticationservice.model.Users;
import org.cripsy.authenticationservice.repository.UsersRepository;
import org.cripsy.authenticationservice.service.JwtService;
import org.cripsy.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Users signup(@RequestBody Users user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Users user) {
        return userService.verifyUser(user);
    }

    @GetMapping("/check-user/{username}")
    public ResponseEntity<UsersDTO> checkUser(@PathVariable String username) {
        UsersDTO foundUser = userService.findUserByUsername(username);
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundUser);
    }
}
