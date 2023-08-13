package com.company.apppcmarket.entity;

import com.company.apppcmarket.entity.abs_entity.MainClass;
import lombok.*;

import jakarta.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Category extends MainClass {

    @ManyToOne
    private Category parentCategory;
}
