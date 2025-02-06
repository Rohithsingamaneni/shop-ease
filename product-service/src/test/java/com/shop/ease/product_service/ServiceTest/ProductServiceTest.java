package com.shop.ease.product_service.ServiceTest;

import com.shop.ease.product_service.dto.ProductDto;
import com.shop.ease.product_service.model.Product;
import com.shop.ease.product_service.repository.CategoryRepository;
import com.shop.ease.product_service.repository.ProductRepository;
import com.shop.ease.product_service.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
//import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void getAllProducts_ShouldReturnPaginatedList(){
        Product product=Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Description")
                .price(150.0)
                .stock(10)
                .build();

        Page<Product> pagedProducts=new PageImpl<>(List.of(product));
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(pagedProducts);

        Page<ProductDto> result=productService.getAllProducts(PageRequest.of(0,10));

        assertEquals(1,result.getTotalElements());
        assertEquals("Test Product",result.getContent().get(0).getName());
        verify(productRepository,times(1)).findAll(any(PageRequest.class));
    }

}
