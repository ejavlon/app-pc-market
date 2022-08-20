package com.example.apppcmarket.entity;

import com.example.apppcmarket.entity.abs_entity.Abs_Class;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"firstName", "lastName", "address_id"}))
public class Customer extends Abs_Class {

}
