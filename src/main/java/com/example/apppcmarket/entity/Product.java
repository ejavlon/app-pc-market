package com.example.apppcmarket.entity;

import com.example.apppcmarket.entity.abs_entity.Main_Class;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Product extends Main_Class {

    @ManyToOne(optional = false)
    private Category category;

    @OneToOne(optional = false)
    private Attachment attachment;

    @Column(nullable = false)
    private Double price;
}
