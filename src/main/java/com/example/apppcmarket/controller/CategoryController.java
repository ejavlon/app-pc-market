package com.example.apppcmarket.controller;

import com.example.apppcmarket.entity.Category;
import com.example.apppcmarket.enums.Elements;
import com.example.apppcmarket.model.CategoryDTO;
import com.example.apppcmarket.model.Result;
import com.example.apppcmarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    Elements message = Elements.CATEGORY;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public HttpEntity<?> getAllCategories(@RequestParam int page) {
        Page<Category> allCategories = categoryService.getAllCategories(page);
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCategoryById(@PathVariable Integer id) {
        Result result = categoryService.getCategoryById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byParentCategoryId/{parent_id}")
    public HttpEntity<?> getCategoriesByParentCategoryId(@PathVariable Integer parent_id) {
        List<Result> results = categoryService.getCategoriesByParentCategoryId(parent_id);
        return ResponseEntity.status(results.get(0).isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(results);
    }

    @PostMapping
    public HttpEntity<?> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Result result = categoryService.addCategory(categoryDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(message.getElementNotFound()) ?
                HttpStatus.NOT_FOUND : result.getMessage().equals(message.getElementIsNotActive()) ? HttpStatus.FORBIDDEN : HttpStatus.CONFLICT).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCategoryById(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Result result = categoryService.editCategoryById(id, categoryDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(message.getElementNotFound()) ?
                HttpStatus.NOT_FOUND : result.getMessage().equals(message.getElementIsNotActive()) ? HttpStatus.FORBIDDEN : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCategoryById(@PathVariable Integer id) {
        Result result = categoryService.deleteCategoryById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
