package com.company.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotNull(message = "Product nomi kiritilmadi")
    private String name;

    private boolean active = true;

    @NotNull(message = "Kategoriya id si kiritilmadi")
    private Integer categoryId;

    @NotNull(message = "Product rasmi kiritilmadi")
    private Integer attachmentId;

    @NotNull(message = "Product narxi kiritilmadi")
    private Double price;
}
