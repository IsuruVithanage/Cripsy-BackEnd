package org.cripsy.OrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.time.LocalDate;
//import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private Integer id;
    private String name;
    private String email;
}
