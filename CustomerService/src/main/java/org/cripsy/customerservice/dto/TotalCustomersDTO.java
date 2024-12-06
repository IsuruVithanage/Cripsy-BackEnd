package org.cripsy.customerservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class TotalCustomersDTO {
    private Long total;
}
