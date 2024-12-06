package org.cripsy.adminservice.service;

import org.cripsy.adminservice.dto.AdminDTO;
import org.cripsy.adminservice.model.Admin;
import org.cripsy.adminservice.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;


    public AdminDTO findAdminByName(String username) {
        //Customer customer = customerRepository.findUserByUsernameAndPassword(username,password);
        Admin admin = adminRepository.findAdminByName(username);
        if (admin != null) {
            AdminDTO dto = new AdminDTO();
            dto.setId((admin.getId()));
            dto.setName(admin.getName());
            dto.setPassword(admin.getPassword());
            dto.setEmail(admin.getEmail());
            return dto;
        }
        return null;
    }



    // Save the Admin entity Service function
    public AdminDTO saveAdmin(AdminDTO adminDTO) {
        Admin admin = modelMapper.map(adminDTO, Admin.class);
        adminRepository.save(admin);
        return adminDTO;
    }

    // Read all Admins Service function
    public List<AdminDTO> getAllAdmins() {
        List<Admin> userList = adminRepository.findAll();
        return modelMapper.map(userList, new TypeToken<List<AdminDTO>>() {
        }.getType());
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
        return modelMapper.map(admin, AdminDTO.class);
    }
}
