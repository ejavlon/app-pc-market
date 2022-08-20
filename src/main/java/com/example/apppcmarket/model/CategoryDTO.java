package com.example.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotNull(message = "nomi kiritilmadi")
    private String name;

    private boolean active = true;

    private Integer parentCategoryId;
}
