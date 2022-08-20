package com.example.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotNull(message = "Product nomi kiritilmadi")
    private String name;

    private boolean active = true;

    @NotNull(message = "Kategoriya id si kiritilmadi")
    private Integer category_id;

    @NotNull(message = "Product rasmi kiritilmadi")
    private Integer attachment_id;

    @NotNull(message = "Product narxi kiritilmadi")
    private Double price;
}
