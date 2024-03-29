package com.company.apppcmarket.repository;

import com.company.apppcmarket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {

    List<Order> findAllByCustomer_Id(Integer customer_id);
}
