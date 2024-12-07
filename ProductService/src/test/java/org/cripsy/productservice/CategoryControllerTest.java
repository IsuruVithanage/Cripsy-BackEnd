package org.cripsy.productservice;

import org.cripsy.productservice.dto.CategoryDTO;
import org.cripsy.productservice.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testAddCategory() throws Exception {
        int categoryId = 1;
        String categoryName = "Electronics";

        CategoryDTO category = new CategoryDTO(categoryId, categoryName);
        Mockito.when(categoryService.createCategory(any())).thenReturn(category);

        String requestBody = """
        {
            "categoryId": %d,
            "categoryName": "%s"
        }
        """.formatted(categoryId, categoryName);

        mockMvc.perform(post("/api/product/category/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryId").value(categoryId))
            .andExpect(jsonPath("$.categoryName").value(categoryName));
    }

    @Test
    public void testUpdateCategory() throws Exception {
        int categoryId = 1;
        String categoryName = "Updated Electronics";

        CategoryDTO category = new CategoryDTO(categoryId, categoryName);
        Mockito.when(categoryService.updateCategory(eq(categoryId), any())).thenReturn(category);

        String requestBody = """
        {
            "categoryId": %d,
            "categoryName": "%s"
        }
        """.formatted(categoryId, categoryName);

        mockMvc.perform(put("/api/product/category/update/{id}", categoryId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryId").value(categoryId))
            .andExpect(jsonPath("$.categoryName").value(categoryName));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        int categoryId = 1;
        String categoryName = "Electronics";

        CategoryDTO category = new CategoryDTO(categoryId, categoryName);
        Mockito.when(categoryService.getCategoryById(categoryId)).thenReturn(category);

        mockMvc.perform(get("/api/product/category/{id}", categoryId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryId").value(categoryId))
            .andExpect(jsonPath("$.categoryName").value(categoryName));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Mockito.when(categoryService.getAllCategories()).thenReturn(Arrays.asList(
            new CategoryDTO(1, "Electronics"),
            new CategoryDTO(2, "Home Appliances")
        ));

        mockMvc.perform(get("/api/product/category/getAll")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].categoryId").value(1))
            .andExpect(jsonPath("$[0].categoryName").value("Electronics"))
            .andExpect(jsonPath("$[1].categoryId").value(2))
            .andExpect(jsonPath("$[1].categoryName").value("Home Appliances"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        int categoryId = 1;

        Mockito.doNothing().when(categoryService).deleteCategory(categoryId);

        mockMvc.perform(delete("/api/product/category/delete/{id}", categoryId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}

