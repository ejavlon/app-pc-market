package com.company.apppcmarket.controller;

import com.company.apppcmarket.entity.Order;
import com.company.apppcmarket.enums.Elements;
import com.company.apppcmarket.model.OrderDTO;
import com.company.apppcmarket.model.Result;
import com.company.apppcmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private Elements messageProducts = Elements.PRODUCT;
    private Elements messageSupplier = Elements.SUPPLIER;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public HttpEntity<?> getAllOrders(@RequestParam(defaultValue = "0") int page) {
        Page<Order> allOrders = orderService.getAllOrders(page);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOrderById(@PathVariable Integer id) {
        Result result = orderService.getOrderById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byCustomerId/{customerId}")
    public HttpEntity<?> getOrdersByCustomerId(@Valid @PathVariable Integer customerId) {
        List<Result> results = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.status(results.get(0).isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(results);
    }

    @PostMapping
    public HttpEntity<?> addOrder(@RequestBody OrderDTO orderDTO) {
        Result result = orderService.addOrder(orderDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : result.getMessage().equals(messageProducts.getElementIsNotActive()) ||
                result.getMessage().equals(messageSupplier.getElementIsNotActive()) ? HttpStatus.FORBIDDEN : HttpStatus.NOT_FOUND).body(result);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editOrderById(@PathVariable Integer id, @Valid @RequestBody OrderDTO orderDTO) {
        Result result = orderService.editOrderById(id, orderDTO);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : result.getMessage().equals(messageProducts.getElementIsNotActive()) ||
                result.getMessage().equals(messageSupplier.getElementIsNotActive()) ? HttpStatus.FORBIDDEN : HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteOrderById(@PathVariable Integer id) {
        Result result = orderService.deleteOrderById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(result);
    }
}
