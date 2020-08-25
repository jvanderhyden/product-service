package com.myretail.product.controller;

import com.myretail.product.dto.ProductDto;
import com.myretail.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProducts(@PathVariable long id) {
        return ResponseEntity.ok(productService.find(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putProducts(@PathVariable long id, @RequestBody ProductDto productDto) {
        productService.save(id, productDto);
        return ResponseEntity.ok().build();
    }
}
