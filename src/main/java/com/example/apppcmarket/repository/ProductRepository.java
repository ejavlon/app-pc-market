package com.example.apppcmarket.repository;

import com.example.apppcmarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findAllByCategory_Id(Integer category_id);

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);
}
