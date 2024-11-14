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
    private  AdminRepository adminRepository;

    @Autowired
    private  ModelMapper modelMapper;

    //Custom exception for duplicate admin ID
    public static class DuplicateAdminIdException extends RuntimeException {
        public DuplicateAdminIdException(String massage){
            super(massage);
        }
    }

    //Custom exception for duplicate Admin ID
    public  AdminDTO saveAdmin(AdminDTO adminDTO){

        Admin admin = modelMapper.map(adminDTO, Admin.class);

        //Check if a student with the same Admin ID already exists
        Optional<Admin> existingAdmin = adminRepository.findById(admin.getId());
                if (existingAdmin.isPresent()){
                    throw new DuplicateAdminIdException("Admin with Id" + admin.getId() + "already exists." );
        }
                //Save the Admin entity Service function
                adminRepository.save(admin);

                return adminDTO;


    }

    //Read the all Admins Service function
    public  List<AdminDTO>getAllAdmins(){
        List<Admin> userList = adminRepository.findAll();
        return modelMapper.map(userList, new TypeToken<List<AdminDTO>>(){
        }.getType());
    }

    //Update Admin Service function
    public AdminDTO updateAdmin(AdminDTO adminDTO) {
        adminRepository.save(modelMapper.map(adminDTO, Admin.class));
        return  adminDTO;
    }

    //Delete Admin Service function
    public AdminDTO deleteAdmin(AdminDTO adminDTO) {
        adminRepository.delete(modelMapper.map(adminDTO, Admin.class));
        return adminDTO;
    }

    //Find Admin by ID Service function
    public AdminDTO getAdminById(Integer id){
        Optional<Admin> admin = adminRepository.findById(id);
        return modelMapper.map(admin,AdminDTO.class);
    }

}
