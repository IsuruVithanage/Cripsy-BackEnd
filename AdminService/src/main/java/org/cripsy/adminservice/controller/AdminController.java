//package org.cripsy.adminservice.controller;
//
//
//import lombok.AllArgsConstructor;
//import org.cripsy.adminservice.dto.AdminDTO;
//import org.cripsy.adminservice.service.AdminService;
//import org.springframework.graphql.data.method.annotation.QueryMapping;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/api/admin")
//public class AdminController {
//
//    private final AdminService adminService;
//
//    //Create Admin
//    @PostMapping("/saveAdmin")
//    public AdminDTO saveAdmin(@RequestBody AdminDTO adminDTO){
//        return adminService.saveAdmin(adminDTO);
//    }
//
//    //Get all Admins
//    @GetMapping("/getAllAdmins")
//    public List<AdminDTO> getAllAdmins(){
//        return adminService.getAllAdmins();
//    }
//
//    @QueryMapping
//    public List<AdminDTO> getAdmin(){ return adminService.getAllAdmins(); }
//
//    //Update Admin
//    @PutMapping("/updateAdmin")
//    public AdminDTO updateAdmin(@RequestBody AdminDTO adminDTO){
//        return adminService.updateAdmin(adminDTO);
//    }
//
//    //Delete Admin
//    @DeleteMapping("/deleteAdmin")
//    public  AdminDTO deleteAdmin(@RequestBody AdminDTO adminDTO){
//        return adminService.deleteAdmin(adminDTO);
//    }
//
//    //Find Admin By ID
//    @GetMapping("/getAdminById/{id}")
//    public AdminDTO getAdminById(@PathVariable Integer id){
//        return adminService.getAdminById(id);
//    }
//}
//
package org.cripsy.adminservice.controller;

import lombok.AllArgsConstructor;
import org.cripsy.adminservice.dto.AdminDTO;
import org.cripsy.adminservice.service.AdminService;

import org.cripsy.orderservice.dto.AdminDashbordDTO;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")  // Allow frontend to make requests
public class AdminController {

    private final AdminService adminService;

    // Create Admin
    @PostMapping("/saveAdmin")
    public AdminDTO saveAdmin(@RequestBody AdminDTO adminDTO){
        return adminService.saveAdmin(adminDTO);
    }

    // Get all Admins
    @GetMapping("/getAllAdmins")
    public List<AdminDTO> getAllAdmins(){
        return adminService.getAllAdmins();
    }

    // Update Admin
    @PutMapping("/updateAdmin")
    public AdminDTO updateAdmin(@RequestBody AdminDTO adminDTO){
        return adminService.updateAdmin(adminDTO);
    }

    // Delete Admin
    @DeleteMapping("/deleteAdmin")
    public AdminDTO deleteAdmin(@RequestBody AdminDTO adminDTO){
        return adminService.deleteAdmin(adminDTO);
    }

    // Find Admin By ID
    @GetMapping("/getAdminById/{id}")
    public AdminDTO getAdminById(@PathVariable Integer id){
        return adminService.getAdminById(id);
    }

    //Get Total Revenue with Moth and Year
    @GetMapping("/getMonthlySumTotal")
    public List<AdminDashbordDTO> getMonthlySumTotal() {return adminService.getMonthlySumTotal();}
}
