package com.myretail.product.controller;

import com.myretail.product.dto.PriceDto;
import com.myretail.product.dto.ProductDto;
import com.myretail.product.service.PriceService;
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

    private final PriceService priceService;
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProducts(@PathVariable long id) {
        ProductDto productDto = productService.getProduct(id);
        if (productDto.getName() == null && productDto.getCurrentPrice() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putProducts(@PathVariable long id, @RequestBody PriceDto priceDto) {
        if (priceDto == null || priceDto.getValue() == null || priceDto.getCurrencyCode() == null) {
            return ResponseEntity.badRequest().build();
        }
        priceService.savePrice(id, priceDto);
        return ResponseEntity.ok().build();
    }
}
