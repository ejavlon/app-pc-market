package com.company.apppcmarket.controller;

import com.company.apppcmarket.entity.Supplier;
import com.company.apppcmarket.enums.Elements;
import com.company.apppcmarket.model.Result;
import com.company.apppcmarket.model.SupplierDTO;
import com.company.apppcmarket.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    private Elements messageAddress = Elements.ADDRESS;
    private Elements messageSupplier = Elements.SUPPLIER;

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public HttpEntity<?> getAllSuppliers(@RequestParam(defaultValue = "0") int page) {
        Page<Supplier> allSuppliers = supplierService.getAllSuppliers(page);
        return ResponseEntity.ok(allSuppliers);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getSupplierById(@PathVariable Integer id) {
        Result result = supplierService.getSupplierById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping
    public HttpEntity<?> addSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        Result result = supplierService.addSupplier(supplierDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(messageAddress.getElementNotFound()) ?
                HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editSupplierById(@PathVariable Integer id, @Valid @RequestBody SupplierDTO supplierDTO) {
        Result result = supplierService.editSupplierById(id, supplierDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageAddress.getElementNotFound()) ||
                result.getMessage().equals(messageSupplier.getElementNotFound()) ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSupplierById(@PathVariable Integer id) {
        Result result = supplierService.deleteSupplierById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
