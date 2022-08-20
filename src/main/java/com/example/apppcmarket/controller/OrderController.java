package com.example.apppcmarket.controller;

import com.example.apppcmarket.entity.Order;
import com.example.apppcmarket.enums.Elements;
import com.example.apppcmarket.model.OrderDTO;
import com.example.apppcmarket.model.Result;
import com.example.apppcmarket.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    Elements messageProducts = Elements.PRODUCT;
    Elements messageSupplier = Elements.SUPPLIER;

    @Autowired
    OrderService orderService;

    @GetMapping
    public HttpEntity<?> getAllOrders(@RequestParam int page) {
        Page<Order> allOrders = orderService.getAllOrders(page);
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOrderById(@PathVariable Integer id) {
        Result result = orderService.getOrderById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/byCustomerId/{customer_id}")
    public HttpEntity<?> getOrdersByCustomerId(@Valid @PathVariable Integer customer_id) {
        List<Result> results = orderService.getOrdersByCustomerId(customer_id);
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
