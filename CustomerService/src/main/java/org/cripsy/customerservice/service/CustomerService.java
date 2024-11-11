package org.cripsy.customerservice.service;

import org.cripsy.customerservice.dto.CustomerDTO;
import org.cripsy.customerservice.model.customer;
import org.cripsy.customerservice.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CustomerDTO> getAllCustomers() {
        List<customer> customers = customerRepository.findAll();
        return modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {}.getType());
    }

    public CustomerDTO getCustomerById(Long id) {
        Optional<customer> customer = customerRepository.findById(id);
        return customer.map(value -> modelMapper.map(value, CustomerDTO.class)).orElse(null);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customer customer = modelMapper.map(customerDTO, customer.class);
        customer = customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        if (customerRepository.existsById(id)) {
            customer customer = modelMapper.map(customerDTO, customer.class);
            customer.setId(id);
            customer = customerRepository.save(customer);
            return modelMapper.map(customer, CustomerDTO.class);
        }
        return null;
    }

    public boolean deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
