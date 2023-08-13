package com.company.apppcmarket.repository;

import com.company.apppcmarket.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    boolean existsByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);

    boolean existsByIdIsNotAndFirstNameAndLastNameAndPhoneNumber(Integer id, String firstName, String lastName, String phoneNumber);

    boolean existsByAddress_Id(Integer address_id);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdIsNotAndPhoneNumber(Integer id, String phoneNumber);

    boolean existsByIdIsNotAndAddress_Id(Integer id, Integer address_id);
}
