package org.cripsy.adminservice.controller;

import org.cripsy.adminservice.dto.AuthDTO;
import org.cripsy.adminservice.model.Admin;
import org.cripsy.adminservice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/check-user")
    public ResponseEntity<AuthDTO> checkUser(@RequestBody Admin adminUser) {
        Admin foundUser = adminRepository.findByEmail(adminUser.getName());
        if (foundUser != null) {
            return ResponseEntity.ok(new AuthDTO(foundUser.getName(), foundUser.getPassword()));
        }
        return ResponseEntity.notFound().build();
    }
}
