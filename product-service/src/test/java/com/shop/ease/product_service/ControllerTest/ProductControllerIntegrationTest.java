package com.shop.ease.product_service.ControllerTest;

import com.shop.ease.product_service.model.Product;
import com.shop.ease.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup(){
        productRepository.deleteAll();
        productRepository.save(Product.builder()
                .name("Sample product")
                .description("Test Description")
                .price(100.0)
                .stock(10)
                .build());
    }

    @Test
    void getAllProducts_ShouldReturnPaginatedProducts() throws Exception{
        mockMvc.perform(get("/api/products")
                .param("page","0")
                .param("size","10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").value("Sample product"));
    }

    @Test
    void addProduct_ShouldSaveProductAndReturnIt() throws Exception{
        String productJson= """
                {
                    "name": "New Product",
                    "description": "Test Description",
                    "price": 100.0,
                    "stock": 10,
                    "categoryName": "Electronics"
                }""";

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.categoryName").value("Electronics"));
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnUpdatedProduct() throws Exception{
        Product product=productRepository.findAll().get(0);
        String updatedProductJson= """
                {
                "name": "Updated Product",
                "description": "Updated Description",
                "price": 150.0,
                "stock": 20,
                "categoryName": "Electronics"
                }
                """;

        mockMvc.perform(put("/api/products/{id}",product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(150.0));
    }

    @Test
    void deleteProduct_ShouldDeleteAndReturnNoContent() throws Exception{
        Product product=productRepository.findAll().get(0);

        mockMvc.perform(delete("/api/products/{id}",product.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/products/{id}",product.getId()))
                .andExpect(status().isNotFound());
    }




}
