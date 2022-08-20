package com.example.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer customer_id;

    private Integer supplier_id;

    private Set<Integer> products;

    private Date date;
}
