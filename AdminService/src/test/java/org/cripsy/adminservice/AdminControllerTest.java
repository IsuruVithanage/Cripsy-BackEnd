package org.cripsy.adminservice;

import org.cripsy.adminservice.dto.AdminDTO;
import org.cripsy.adminservice.dto.GetBestSellingDTO;
import org.cripsy.adminservice.service.AdminService;
import org.cripsy.orderservice.dto.AdminDashbordDTO;
import org.cripsy.orderservice.dto.MonthlyTotalPriceDTO;
import org.cripsy.orderservice.dto.TotalItemDTO;
import org.cripsy.orderservice.dto.TotalOrdersDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    void saveAdminTest() throws Exception {
        String requestBody = """
            {
              "id": 0,
              "name": "string",
              "email": "string",
              "password": "string",
              "contact": "string"
            }
        """;

        Mockito.when(adminService.saveAdmin(any(AdminDTO.class))).thenReturn(new AdminDTO());

        mockMvc.perform(post("/api/admin/saveAdmin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void updateAdminTest() throws Exception {
        String requestBody = """
            {
              "id": 0,
              "name": "string",
              "email": "string",
              "password": "string",
              "contact": "string"
            }
        """;

        Mockito.when(adminService.updateAdmin(any(AdminDTO.class))).thenReturn(new AdminDTO());

        mockMvc.perform(put("/api/admin/updateAdmin")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void deleteAdminTest() throws Exception {
        String requestBody = """
            {
              "id": 0,
              "name": "string",
              "email": "string",
              "password": "string",
              "contact": "string"
            }
        """;

        Mockito.when(adminService.deleteAdmin(any(AdminDTO.class))).thenReturn(new AdminDTO());

        mockMvc.perform(get("/api/admin/best-selling")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void loginTest() throws Exception {
        String requestBody = """
            {
                "username": "string",
                "email": "string",
                "password": "string"
            }
        """;

        Mockito.when(adminService.findAdminByUsername(anyString())).thenReturn(new AdminDTO());

        mockMvc.perform(post("/api/admin/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    void getTotalCustomersTest() throws Exception {
        Mockito.when(adminService.getTotalCustomers()).thenReturn(100L);

        mockMvc.perform(get("/api/admin/totalCustomer")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("100"));
    }

    @Test
    void getOrderStatsTest() throws Exception {
        Mockito.when(adminService.getOrderStats()).thenReturn(List.of(new TotalOrdersDTO()));

        mockMvc.perform(get("/api/admin/orderSummery")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getAllAdminsTest() throws Exception {
        Mockito.when(adminService.getAllAdmins()).thenReturn(List.of(new AdminDTO()));

        mockMvc.perform(get("/api/admin/getAllAdmins")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getAdminByIdTest() throws Exception {
        Mockito.when(adminService.getAdminById(anyInt())).thenReturn(new AdminDTO());

        mockMvc.perform(get("/api/admin/getAdminById/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getMonthlyTotalsTest() throws Exception {
        Mockito.when(adminService.findMonthlyTotalPrices()).thenReturn(List.of(new MonthlyTotalPriceDTO()));

        mockMvc.perform(get("/api/admin/monthly-totals")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getMonthlySumTotalTest() throws Exception {
        Mockito.when(adminService.getMonthlySumTotal()).thenReturn(List.of(new AdminDashbordDTO()));

        mockMvc.perform(get("/api/admin/getMonthlySumTotal")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getMonthlySumQtyTest() throws Exception {
        Mockito.when(adminService.getMonthlySumQty()).thenReturn(List.of(new TotalItemDTO()));

        mockMvc.perform(get("/api/admin/getMonthlySumQty")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void getBestSellingTest() throws Exception {
        Mockito.when(adminService.getBestSellingProducts()).thenReturn(List.of(new GetBestSellingDTO()));

        mockMvc.perform(get("/api/admin/best-selling")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}

