package com.company.apppcmarket.service;

import com.company.apppcmarket.entity.Customer;
import com.company.apppcmarket.entity.Order;
import com.company.apppcmarket.entity.Product;
import com.company.apppcmarket.entity.Supplier;
import com.company.apppcmarket.enums.Elements;
import com.company.apppcmarket.model.OrderDTO;
import com.company.apppcmarket.model.Result;
import com.company.apppcmarket.repository.CustomerRepository;
import com.company.apppcmarket.repository.OrderRepository;
import com.company.apppcmarket.repository.ProductRepository;
import com.company.apppcmarket.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    Elements messageOrder = Elements.ORDER;

    Elements messageCustomer = Elements.CUSTOMER;

    Elements messageSupplier = Elements.SUPPLIER;

    Elements messageProduct = Elements.PRODUCT;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ProductRepository productRepository;

    public Page<Order> getAllOrders(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return orderRepository.findAll(pageable);
    }

    public Result getOrderById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.map(order -> new Result(true, order)).orElseGet(() -> new Result(messageOrder.getElementNotFound(), false));
    }

    public List<Result> getOrdersByCustomerId(Integer customerId) {
        List<Result> results = new ArrayList<>();
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            List<Order> orders = orderRepository.findAllByCustomer_Id(customerId);
            for (Order order : orders) {
                Result result = new Result(true, order);
                results.add(result);
            }
            return results;
        }
        Result result = new Result(messageCustomer.getElementNotFound(), false);
        results.add(result);
        return results;
    }

    private Result addingOrder(OrderDTO orderDTO) {
        Order order = new Order();

        Optional<Customer> optionalCustomer = customerRepository.findById(orderDTO.getCustomerId());
        if (!optionalCustomer.isPresent()) {
            return new Result(messageCustomer.getElementNotFound(), false);
        }
        Customer customer = optionalCustomer.get();

        Optional<Supplier> optionalSupplier = supplierRepository.findById(orderDTO.getSupplierId());
        if (!optionalSupplier.isPresent()) {
            return new Result(messageSupplier.getElementNotFound(), false);
        }
        Supplier supplier = optionalSupplier.get();
        if (!supplier.isActive()) {
            return new Result(messageSupplier.getElementIsNotActive(), false);
        }

        Set<Product> products = new HashSet<>();
        Set<Integer> products_ids = orderDTO.getProducts();
        for (Integer products_id : products_ids) {
            Optional<Product> optionalProduct = productRepository.findById(products_id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                if (!product.isActive()) {
                    return new Result(messageProduct.getElementIsNotActive(), false);
                }
                products.add(product);
            }
            else
                return new Result(messageProduct.getElementNotFound(), false);
        }
        order.setCustomer(customer);
        order.setDate(orderDTO.getDate());
        order.setProducts(products);
        order.setSupplier(supplier);
        return new Result(true, order);
    }

    public Result addOrder(OrderDTO orderDTO) {
        Result result = addingOrder(orderDTO);
        if (result.isSuccess()) {
            Order order = (Order) result.getObject();
            orderRepository.save(order);
            return new Result(messageOrder.getElementAdded(), true);
        }
        return result;
    }

    public Result editOrderById(Integer id, OrderDTO orderDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Result result = addingOrder(orderDTO);
            if (result.isSuccess()) {
                Order editOrder = optionalOrder.get();
                Order order = (Order) result.getObject();
                editOrder.setSupplier(order.getSupplier());
                editOrder.setDate(order.getDate());
                editOrder.setProducts(order.getProducts());
                editOrder.setCustomer(order.getCustomer());
                orderRepository.save(editOrder);
                return new Result(messageOrder.getElementEdited(), true);
            }
            return result;
        }
        return new Result(messageOrder.getElementNotFound(), false);
    }

    public Result deleteOrderById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            orderRepository.delete(optionalOrder.get());
            return new Result(messageOrder.getElementDeleted(), true);
        }
        return new Result(messageOrder.getElementNotFound(), false);
    }
}
