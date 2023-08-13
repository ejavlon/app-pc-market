package com.company.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotNull(message = "nom kiritilmadi")
    private String name;

    private boolean active = true;

    private Integer parentCategoryId;
}
