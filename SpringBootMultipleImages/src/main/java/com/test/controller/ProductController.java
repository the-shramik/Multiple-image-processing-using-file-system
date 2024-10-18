package com.test.controller;

import com.test.model.Product;
import com.test.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productPrice") Double productPrice,
            @RequestParam("productImages")List<MultipartFile> productImages
    ) throws IOException {
           Product product=new Product();
           product.setProductName(productName);
           product.setProductPrice(productPrice);
           return ResponseEntity.ok(productService.addProduct(product, productImages));
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }
}
