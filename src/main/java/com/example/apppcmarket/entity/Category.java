package com.example.apppcmarket.entity;

import com.example.apppcmarket.entity.abs_entity.Main_Class;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Category extends Main_Class {

    @ManyToOne
    private Category parentCategory;
}
