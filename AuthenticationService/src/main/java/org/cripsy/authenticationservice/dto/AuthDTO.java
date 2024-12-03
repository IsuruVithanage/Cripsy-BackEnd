package org.cripsy.authenticationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthDTO {
    private String username;
    private String email;
    private String password;
}
