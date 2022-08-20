package com.example.apppcmarket.repository;

import com.example.apppcmarket.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

    boolean existsByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);

    boolean existsByIdIsNotAndFirstNameAndLastNameAndPhoneNumber(Integer id, String firstName, String lastName, String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdIsNotAndPhoneNumber(Integer id, String phoneNumber);

    boolean existsByAddress_Id(Integer address_id);

    boolean existsByIdIsNotAndAddress_Id(Integer id, Integer address_id);
}
