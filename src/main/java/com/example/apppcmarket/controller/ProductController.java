package com.example.apppcmarket.controller;

import com.example.apppcmarket.entity.Product;
import com.example.apppcmarket.enums.Elements;
import com.example.apppcmarket.model.ProductDTO;
import com.example.apppcmarket.model.Result;
import com.example.apppcmarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    Elements messageProduct = Elements.PRODUCT;
    Elements messageCategory = Elements.CATEGORY;

    @Autowired
    ProductService productService;

    @GetMapping
    public HttpEntity<?> getAllProducts(@RequestParam int page) {
        Page<Product> allProducts = productService.getAllProducts(page);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getProductById(@PathVariable Integer id) {
        Result result = productService.getProductById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byCategoryId/{category_id}")
    public HttpEntity<?> getProductsByCategoryId(@PathVariable Integer category_id) {
        List<Result> results = productService.getProductsByCategoryId(category_id);
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
