package com.company.apppcmarket.projection;

import com.company.apppcmarket.entity.Address;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Address.class)
public interface CustomAddress {

    Integer getId();

    String getCity();

    String getStreet();

    Integer getHomeNumber();
}
