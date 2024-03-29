package com.github.hh.backend.controller;

import com.github.hh.backend.model.Product;
import com.github.hh.backend.model.ProductChangeDTO;
import com.github.hh.backend.model.ProductDTO;
import com.github.hh.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody ProductDTO productDTO){
        return productService.addProduct(productDTO);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable String id){
        productService.deleteProductById(id);
    }

    @GetMapping("/critical")
    public List<Product> getProductsInCriticalStock() {
        return productService.getProductsInCriticalStock();
    }

    @GetMapping("/changelog")
    public List<ProductChangeDTO> getChangeLog() {
        return productService.getChangeLog();
    }

    @PostMapping("/bulk")
    public void addBulkProducts(@RequestBody List<ProductDTO> productDTOs) {
        for(ProductDTO productDTO : productDTOs) {
            productService.addProduct(productDTO);
        }
    }
}
