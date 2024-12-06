

package org.cripsy.adminservice.service;

import org.cripsy.adminservice.dto.AdminDTO;
import org.cripsy.adminservice.model.Admin;
import org.cripsy.adminservice.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.cripsy.orderservice.dto.AdminDashbordDTO;
import org.cripsy.orderservice.dto.TotalItemDTO;
import org.cripsy.orderservice.dto.TotalOrdersDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final WebClient webClient;

    public AdminService(WebClient webClient) {
        this.webClient = webClient;
    }

    // Save the Admin entity Service function
    public AdminDTO saveAdmin(AdminDTO adminDTO) {
        Admin admin = modelMapper.map(adminDTO, Admin.class);
        adminRepository.save(admin);
        return adminDTO; // Ensure we return the saved data in the expected format
    }

    // Read all Admins Service function
    public List<AdminDTO> getAllAdmins() {
        List<Admin> userList = adminRepository.findAll();
        return modelMapper.map(userList, new TypeToken<List<AdminDTO>>() {}.getType());
    }

    // Update Admin Service function
    public AdminDTO updateAdmin(AdminDTO adminDTO) {
        adminRepository.save(modelMapper.map(adminDTO, Admin.class));
        return adminDTO;
    }

    // Delete Admin Service function
    public AdminDTO deleteAdmin(AdminDTO adminDTO) {
        adminRepository.delete(modelMapper.map(adminDTO, Admin.class));
        return adminDTO;
    }

    // Find Admin by ID Service function
    public AdminDTO getAdminById(Integer id) {
        Optional<Admin> admin = adminRepository.findById(id);
        return modelMapper.map(admin.orElse(null), AdminDTO.class); // Avoiding null values
    }


    //Get the Monthly Revenue
    public List<AdminDashbordDTO> getMonthlySumTotal() {
        return webClient.get()
                .uri("http://localhost:8083/api/orders/getMonthlySumTotal")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AdminDashbordDTO>>() {})
                .block();
    }

    //Get the Mothly Total Selling Items
    public List<TotalItemDTO> getMonthlySumQty(){
        return webClient.get()
                .uri("http://localhost:8083/api/orders/getMonthlySumQty")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TotalItemDTO>>() {})
                .block();
    }

    //Get Total Orders
    public  List<TotalOrdersDTO> getOrderStats() {
         return webClient.get()
                .uri("http://localhost:8083/api/orders/orderSummery")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TotalOrdersDTO>>() {})
                .block();

    }

    //Get Total Customers
    public Long getTotalCustomers() {
        return webClient.get()
                .uri("http://localhost:8081/api/customers/total")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
    }



}
