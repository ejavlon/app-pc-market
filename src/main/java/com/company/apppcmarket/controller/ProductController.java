package com.company.apppcmarket.controller;

import com.company.apppcmarket.entity.Product;
import com.company.apppcmarket.enums.Elements;
import com.company.apppcmarket.model.ProductDTO;
import com.company.apppcmarket.model.Result;
import com.company.apppcmarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

   private Elements messageProduct = Elements.PRODUCT;
   private Elements messageCategory = Elements.CATEGORY;

    @Autowired
    private ProductService productService;

    @GetMapping
    public HttpEntity<?> getAllProducts(@RequestParam(defaultValue = "0") int page) {
        Page<Product> allProducts = productService.getAllProducts(page);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProductById(@PathVariable Integer id) {
        Result result = productService.getProductById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byCategoryId/{categoryId}")
    public HttpEntity<?> getProductsByCategoryId(@PathVariable Integer categoryId) {
        List<Result> results = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.status(results.get(0).isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(results);
    }

    @PostMapping
    public HttpEntity<?> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        Result result = productService.addProduct(productDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(messageCategory.getElementIsNotActive()) ?
                HttpStatus.FORBIDDEN : result.getMessage().equals(messageProduct.getElementExists()) ? HttpStatus.CONFLICT : HttpStatus.NOT_FOUND).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editProductById(@PathVariable Integer id, @Valid @RequestBody ProductDTO productDTO) {
        Result result = productService.editProductById(id, productDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageCategory.getElementIsNotActive()) ?
                HttpStatus.FORBIDDEN : result.getMessage().equals(messageProduct.getElementExists()) ? HttpStatus.CONFLICT : HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProductById(@PathVariable Integer id) {
        Result result = productService.deleteProductById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
