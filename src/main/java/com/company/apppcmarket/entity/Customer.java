package com.company.apppcmarket.entity;

import com.company.apppcmarket.entity.abs_entity.AbsClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"firstName", "lastName", "address_id"}))
public class Customer extends AbsClass {

}
