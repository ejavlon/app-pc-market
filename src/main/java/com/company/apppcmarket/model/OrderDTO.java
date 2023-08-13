package com.company.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer customerId;

    private Integer supplierId;

    private Set<Integer> products;

    private Date date;
}
