package com.company.apppcmarket.entity;

import com.company.apppcmarket.entity.abs_entity.MainClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Product extends MainClass {

    @ManyToOne(optional = false)
    private Category category;

    @OneToOne(optional = false)
    private Attachment attachment;

    @Column(nullable = false)
    private Double price;
}
