package com.shop.ease.product_service.ControllerTest;

import com.shop.ease.product_service.model.Category;
import com.shop.ease.product_service.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup(){
        categoryRepository.deleteAll();
        categoryRepository.save(Category.builder().name("Electronics").build());
    }

    @Test
    void getAllCategory_ShouldReturnAllCategories() throws Exception{
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Electronics"));
    }

    @Test
    void addCategory_ShouldAddCategory() throws Exception{
        String addCategory= """
                {
                "name": "Books"
                }
                """;

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addCategory))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Books"));
    }

    @Test
    void updateCategory_ShouldUpdateCategoryDetails() throws Exception{
        Category category=categoryRepository.findAll().get(0);
        String updateCategory= """
                {
                "name": "HairProduct"
                }
                """;

        mockMvc.perform(put("/api/categories/{id}",category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateCategory))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HairProduct"));
    }

    @Test
    void deleteCategory_ShouldDeleteCategory() throws Exception{
        Category category=categoryRepository.findAll().get(0);

        mockMvc.perform(delete("/api/categories/{id}",category.getId()))
                .andExpect(status().isNoContent());
    }
}
