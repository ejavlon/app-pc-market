package com.company.apppcmarket.service;

import com.company.apppcmarket.entity.Address;
import com.company.apppcmarket.entity.Supplier;
import com.company.apppcmarket.enums.Elements;
import com.company.apppcmarket.model.Result;
import com.company.apppcmarket.model.SupplierDTO;
import com.company.apppcmarket.repository.AddressRepository;
import com.company.apppcmarket.repository.CustomerRepository;
import com.company.apppcmarket.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierService {

    Elements messageSupplier = Elements.SUPPLIER;
    Elements messageAddress = Elements.ADDRESS;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Page<Supplier> getAllSuppliers(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return supplierRepository.findAll(pageable);
    }

    public Result getSupplierById(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        return optionalSupplier.map(supplier -> new Result(true, supplier)).orElseGet(() -> new Result(messageSupplier.getElementNotFound(), false));
    }

    private Result addingSupplier(SupplierDTO supplierDTO, boolean create, boolean edit, Integer id) {
        Supplier supplier = new Supplier();
        if (create && supplierRepository.existsByFirstNameAndLastNameAndPhoneNumber(supplierDTO.getFirstName(), supplierDTO.getLastName(),
                supplierDTO.getPhoneNumber()) || edit && supplierRepository.existsByIdIsNotAndFirstNameAndLastNameAndPhoneNumber(id, supplierDTO.getFirstName(),
                supplierDTO.getLastName(), supplierDTO.getPhoneNumber())) {
            return new Result(messageSupplier.getElementExists(), false);
        }
        if (create && supplierRepository.existsByPhoneNumber(supplierDTO.getPhoneNumber()) ||
                edit && supplierRepository.existsByIdIsNotAndPhoneNumber(id, supplierDTO.getPhoneNumber())) {
            return new Result("Bunday telefon nomer bor", false);
        }
        Optional<Address> optionalAddress = addressRepository.findById(supplierDTO.getAddressId());
        if (!optionalAddress.isPresent()) {
            return new Result(messageAddress.getElementNotFound(), false);
        }
        Address address = optionalAddress.get();

        if (create && supplierRepository.existsByAddress_Id(supplierDTO.getAddressId()) ||
                edit && supplierRepository.existsByIdIsNotAndAddress_Id(id, supplierDTO.getAddressId())) {
            return new Result("Bu manzilda boshqa ta'minotchi yashaydi", false);
        }
        if (create && customerRepository.existsByAddress_Id(supplierDTO.getAddressId()) ||
                edit && customerRepository.existsByIdIsNotAndAddress_Id(id, supplierDTO.getAddressId())) {
            return new Result("Bu manzilda mijoz yashaydi", false);
        }

        supplier.setActive(supplierDTO.isActive());
        supplier.setAddress(address);
        supplier.setFirstName(supplierDTO.getFirstName());
        supplier.setLastName(supplierDTO.getLastName());
        supplier.setPhoneNumber(supplierDTO.getPhoneNumber());
        return new Result(true, supplier);
    }

    public Result addSupplier(SupplierDTO supplierDTO) {
        Result result = addingSupplier(supplierDTO, true, false, null);
        if (result.isSuccess()) {
            Supplier supplier = (Supplier) result.getObject();
            supplierRepository.save(supplier);
            return new Result(messageSupplier.getElementAdded(), true);
        }
        return result;
    }

    public Result editSupplierById(Integer id, SupplierDTO supplierDTO) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Result result = addingSupplier(supplierDTO, false, true, id);
            if (result.isSuccess()) {
                Supplier editSupplier = optionalSupplier.get();
                Supplier supplier = (Supplier) result.getObject();
                editSupplier.setPhoneNumber(supplier.getPhoneNumber());
                editSupplier.setAddress(supplier.getAddress());
                editSupplier.setLastName(supplier.getLastName());
                editSupplier.setFirstName(supplier.getFirstName());
                editSupplier.setActive(supplier.isActive());
                supplierRepository.save(editSupplier);
                return new Result(messageSupplier.getElementEdited(), true);
            }
            return result;
        }
        return new Result(messageSupplier.getElementNotFound(), false);
    }

    public Result deleteSupplierById(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            supplierRepository.delete(optionalSupplier.get());
            return new Result(messageSupplier.getElementDeleted(), true);
        }
        return new Result(messageSupplier.getElementNotFound(), false);
    }
}
