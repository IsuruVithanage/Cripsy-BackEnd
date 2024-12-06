package org.cripsy.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthDTO {
    private String username;
    private String email;
    private String password;
}