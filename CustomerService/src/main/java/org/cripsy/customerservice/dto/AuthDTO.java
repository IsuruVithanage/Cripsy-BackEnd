package org.cripsy.customerservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    private String username;
    private String email;
    private String password;
}
