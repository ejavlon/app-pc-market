package com.company.apppcmarket.repository;

import com.company.apppcmarket.entity.Address;
import com.company.apppcmarket.projection.CustomAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(path = "address",excerptProjection = CustomAddress.class)
public interface AddressRepository extends JpaRepository<Address,Integer> {
}
