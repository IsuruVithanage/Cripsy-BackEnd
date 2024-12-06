package org.cripsy.customerservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String contact;
    private String district;
}
