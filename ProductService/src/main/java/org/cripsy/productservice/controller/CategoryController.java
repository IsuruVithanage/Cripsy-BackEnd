package org.cripsy.productservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.cripsy.productservice.dto.CategoryDTO;
import org.cripsy.productservice.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/getAll")
    @Operation(summary = "Get All Categories", description = "Fetch a list of all available categories.", tags = "Admin")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Category", description = "Fetch the details of a single category by its ID.", tags = "Admin")
    public CategoryDTO getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/add")
    @Operation(summary = "Add a new category", description = "Add a new category to the system.", tags = "Admin")
    public CategoryDTO addCategory(@Valid @RequestBody CategoryDTO categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a category", description = "Update the details of an existing category.", tags = "Admin")
    public CategoryDTO updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a category", description = "Delete a category specified by its ID.", tags = "Admin")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return "Category with ID " + id + " deleted successfully.";
    }
}
