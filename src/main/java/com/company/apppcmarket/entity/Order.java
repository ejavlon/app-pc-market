package com.company.apppcmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Bitta mijoz bir nechta zakaz qiladi
     * bitta zakaz bitta mijozga tegishli bo'ladi
     */
    @ManyToOne
    private Customer customer;

    /**
     * Bir necha buyurtmani bitta dostavkachi dostavka qiladi
     * bitta buyurtma bitta dostavkachiga tegishli
     */
    @ManyToOne
    private Supplier supplier;

    /**
     * Bitta buyurtmada bir necha product zakaz qilinadi
     * Bitta product bir necha marta zakaz qilinadi
     */
    @ManyToMany
    private Set<Product> products;

    /**
     * Zakaz qilingan sana
     */
    private Date date;
}
