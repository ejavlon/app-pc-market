package com.example.apppcmarket.controller;

import com.example.apppcmarket.entity.Customer;
import com.example.apppcmarket.enums.Elements;
import com.example.apppcmarket.model.CustomerDTO;
import com.example.apppcmarket.model.Result;
import com.example.apppcmarket.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    Elements messageAddress = Elements.ADDRESS;
    Elements messageCustomer = Elements.CUSTOMER;

    @Autowired
    CustomerService customerService;

    @GetMapping
    public HttpEntity<?> getAllCustomers(@RequestParam int page) {
        Page<Customer> allCustomers = customerService.getAllCustomers(page);
        return ResponseEntity.ok(allCustomers);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCustomerById(@PathVariable Integer id) {
        Result result = customerService.getCustomerById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping
    public HttpEntity<?> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Result result = customerService.addCustomer(customerDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : result.getMessage().equals(messageAddress.getElementNotFound()) ?
                HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCustomerById(@PathVariable Integer id, @Valid @RequestBody CustomerDTO customerDTO) {
        Result result = customerService.editCustomerById(id, customerDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageAddress.getElementNotFound()) ||
                result.getMessage().equals(messageCustomer.getElementNotFound()) ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteCustomerById(@PathVariable Integer id) {
        Result result = customerService.deleteCustomerById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
