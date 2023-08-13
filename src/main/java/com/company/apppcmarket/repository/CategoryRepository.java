package com.company.apppcmarket.repository;

import com.company.apppcmarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findAllByParentCategory_Id(Integer parentCategory_id);

    boolean existsByName(String name);

    boolean existsByIdIsNotAndName(Integer id, String name);

    boolean existsByParentCategory_Id(Integer parentCategory_id);
}
