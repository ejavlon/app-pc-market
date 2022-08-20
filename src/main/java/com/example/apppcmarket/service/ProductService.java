package com.example.apppcmarket.service;

import com.example.apppcmarket.entity.Attachment;
import com.example.apppcmarket.entity.Category;
import com.example.apppcmarket.entity.Product;
import com.example.apppcmarket.enums.Elements;
import com.example.apppcmarket.model.ProductDTO;
import com.example.apppcmarket.model.Result;
import com.example.apppcmarket.repository.AttachmentRepository;
import com.example.apppcmarket.repository.CategoryRepository;
import com.example.apppcmarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    Elements messageProduct = Elements.PRODUCT;
    Elements messageCategory = Elements.CATEGORY;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public Page<Product> getAllProducts(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.findAll(pageable);
    }

    public Result getProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.map(product -> new Result(true, product)).orElseGet(() -> new Result(messageProduct.getElementNotFound(), false));
    }

    public List<Result> getProductsByCategoryId(Integer category_id) {
        List<Result> results = new ArrayList<>();
        Optional<Category> optionalCategory = categoryRepository.findById(category_id);
        if (optionalCategory.isPresent()) {
            List<Product> products = productRepository.findAllByCategory_Id(category_id);
            for (Product product : products) {
                Result result = new Result(true, product);
                results.add(result);
            }
            return results;
        }
        Result result = new Result(messageCategory.getElementNotFound(), false);
        results.add(result);
        return results;
    }

    private Result addingProduct(ProductDTO productDTO, boolean create, boolean edit, Integer id) {
        Product product = new Product();
        if (create && productRepository.existsByName(productDTO.getName()) ||
                edit && productRepository.existsByIdIsNotAndName(id, productDTO.getName())) {
            return new Result(messageProduct.getElementExists(), false);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategory_id());
        if (!optionalCategory.isPresent()) {
            return new Result(messageCategory.getElementNotFound(), false);
        }
        Category category = optionalCategory.get();
        if (!category.isActive()) {
            return new Result(messageCategory.getElementIsNotActive(), false);
        }

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDTO.getAttachment_id());
        if (!optionalAttachment.isPresent()) {
            return new Result("Fayl topilmadi", false);
        }
        Attachment attachment = optionalAttachment.get();

        product.setPrice(productDTO.getPrice());
        product.setActive(productDTO.isActive());
        product.setAttachment(attachment);
        product.setCategory(category);
        product.setName(productDTO.getName());
        return new Result(true, product);
    }

    public Result addProduct(ProductDTO productDTO) {
        Result result = addingProduct(productDTO, true, false, null);
        if (result.isSuccess()) {
            Product product = (Product) result.getObject();
            productRepository.save(product);
            return new Result(messageProduct.getElementAdded(), true);
        }
        return result;
    }

    public Result editProductById(Integer id, ProductDTO productDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Result result = addingProduct(productDTO, false, true, id);
            if (result.isSuccess()) {
                Product editProduct = optionalProduct.get();
                Product product = (Product) result.getObject();
                editProduct.setName(product.getName());
                editProduct.setActive(product.isActive());
                editProduct.setPrice(product.getPrice());
                editProduct.setCategory(product.getCategory());
                editProduct.setAttachment(product.getAttachment());
                productRepository.save(editProduct);
                return new Result(messageProduct.getElementEdited(), true);
            }
            return result;
        }
        return new Result(messageProduct.getElementNotFound(), false);
    }

    public Result deleteProductById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.delete(optionalProduct.get());
            return new Result(messageProduct.getElementDeleted(), true);
        }
        return new Result(messageProduct.getElementNotFound(), false);
    }
}
