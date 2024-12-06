package org.cripsy.customerservice.controller;

import org.cripsy.customerservice.dto.AuthDTO;
import org.cripsy.customerservice.dto.CustomerDTO;
import org.cripsy.customerservice.model.Customer;
import org.cripsy.customerservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("signup")
    public ResponseEntity<String> createCustomer(@RequestBody AuthDTO authDTO) {
        customerService.saveCustomer(authDTO);
        return ResponseEntity.ok("Customer saved successfully!");
    }

    @PostMapping("login")
    public ResponseEntity<CustomerDTO> findCustomerByUsername(@RequestBody AuthDTO authDTO) {
        CustomerDTO customer = customerService.findCustomerByUsername(authDTO.getUsername());
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

//    @PostMapping
//    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
//        return customerService.createCustomer(customerDTO);
//    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/total")
    public long getTotalCustomers() {
        return customerService.getTotalCustomers();
    }


}
