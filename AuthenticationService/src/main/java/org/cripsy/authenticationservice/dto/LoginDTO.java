package org.cripsy.authenticationservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO {
    private Long id;
    private String userName;
    private String email;
    private String password;
}
