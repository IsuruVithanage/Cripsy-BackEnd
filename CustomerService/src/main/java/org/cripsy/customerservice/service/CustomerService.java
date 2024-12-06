package org.cripsy.customerservice.service;

import org.cripsy.customerservice.dto.AuthDTO;
import org.cripsy.customerservice.dto.CustomerDTO;
import org.cripsy.customerservice.model.Customer;
import org.cripsy.customerservice.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void saveCustomer(AuthDTO authDTO) {
        Customer customer = modelMapper.map(authDTO, Customer.class);
        customerRepository.save(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return modelMapper.map(customers, new TypeToken<List<CustomerDTO>>() {
        }.getType());
    }

    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> modelMapper.map(value, CustomerDTO.class)).orElse(null);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer = customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        if (customerRepository.existsById(id)) {
            Customer customer = modelMapper.map(customerDTO, Customer.class);
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

    // public Customer getCustomerById(Integer id) {
    // Optional<Customer> customer = customerRepository.findById(id);
    // return customer.orElse(null);
    // }
    // Get the total numbers of customers
    public long getTotalCustomers() {
        return customerRepository.getTotalCustomers();
    }

}
