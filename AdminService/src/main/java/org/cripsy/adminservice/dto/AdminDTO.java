package org.cripsy.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private  Integer id;
    private  String name;
    private  String email;
    private  String password;
    private  String contact;
}
