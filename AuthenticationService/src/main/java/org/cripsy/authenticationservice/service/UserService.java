package org.cripsy.authenticationservice.service;

import org.cripsy.authenticationservice.dto.UsersDTO;
import org.cripsy.authenticationservice.model.Users;
import org.cripsy.authenticationservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    public Users createUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }


    public String verifyUser(Users user) {
        Authentication auth =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }

        return "Invalid username or password";
    }


    public UsersDTO findUserByUsername(String username) {
        Users foundUser = usersRepository.findUserByUsername(username);
        if (foundUser == null) {
            return null;
        }

        UsersDTO userDTO = new UsersDTO();
        userDTO.setUsername(foundUser.getUsername());
        userDTO.setPassword(foundUser.getPassword());
        return userDTO;
    }
}
