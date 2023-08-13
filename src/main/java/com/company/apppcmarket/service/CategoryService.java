package com.company.apppcmarket.service;

import com.company.apppcmarket.entity.Category;
import com.company.apppcmarket.enums.Elements;
import com.company.apppcmarket.model.CategoryDTO;
import com.company.apppcmarket.model.Result;
import com.company.apppcmarket.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    Elements message = Elements.CATEGORY;

    @Autowired
    CategoryRepository categoryRepository;

    public Page<Category> getAllCategories(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return categoryRepository.findAll(pageable);
    }

    public Result getCategoryById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.map(category -> new Result(true, category)).orElseGet(() -> new Result(message.getElementNotFound(), false));
    }

    public List<Result> getCategoriesByParentCategoryId(Integer parent_id) {
        List<Result> results = new ArrayList<>();
        if (categoryRepository.existsByParentCategory_Id(parent_id)) {
            List<Category> categories = categoryRepository.findAllByParentCategory_Id(parent_id);
            for (Category category : categories) {
                Result result = new Result(true, category);
                results.add(result);
            }
            return results;
        }
        Result result = new Result(message.getElementNotFound(), false);
        results.add(result);
        return results;
    }

    private Result addingCategory(CategoryDTO categoryDTO, boolean create, boolean edit, Integer id) {
        Category category = new Category();
        if (create && categoryRepository.existsByName(categoryDTO.getName()) ||
                edit && categoryRepository.existsByIdIsNotAndName(id, categoryDTO.getName())) {
            return new Result(message.getElementExists(), false);
        }

        if(categoryDTO.getParentCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getParentCategoryId());
            if (!optionalCategory.isPresent()) {
                return new Result(message.getElementNotFound(), false);
            }
            Category parentCategory = optionalCategory.get();
            if (!parentCategory.isActive()) {
                return new Result(message.getElementIsNotActive(), false);
            }
            category.setParentCategory(parentCategory);
        }

        category.setActive(categoryDTO.isActive());
        category.setName(categoryDTO.getName());
        return new Result(true, category);
    }

    public Result addCategory(CategoryDTO categoryDTO) {
        Result result = addingCategory(categoryDTO, true, false, null);
        if (result.isSuccess()) {
            Category category = (Category) result.getObject();
            categoryRepository.save(category);
            return new Result(message.getElementAdded(), true);
        }
        return result;
    }

    public Result editCategoryById(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Result result = addingCategory(categoryDTO, false, true, id);
            if (result.isSuccess()) {
                Category editCategory = optionalCategory.get();
                Category category = (Category) result.getObject();
                editCategory.setParentCategory(category.getParentCategory());
                editCategory.setName(category.getName());
                editCategory.setActive(category.isActive());
                categoryRepository.save(editCategory);
                return new Result(message.getElementEdited(), true);
            }
            return result;
        }
        return new Result(message.getElementNotFound(), false);
    }

    public Result deleteCategoryById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            categoryRepository.delete(optionalCategory.get());
            return new Result(message.getElementDeleted(), true);
        }
        return new Result(message.getElementNotFound(), false);
    }
}
