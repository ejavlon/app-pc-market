package com.example.apppcmarket.projection;

import com.example.apppcmarket.entity.Address;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Address.class)
public interface CustomAddress {

    Integer getId();

    String getCity();

    String getStreet();

    Integer getHomeNumber();
}
