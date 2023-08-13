package com.company.apppcmarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    @NotNull(message = "Ism kiritilmadi")
    private String firstName;

    @NotNull(message = "Familiya kiritilmadi")
    private String lastName;

    @NotNull(message = "Telefon nomer kiritilmadi")
    private String phoneNumber;

    @NotNull(message = "Address id kiritilmadi")
    @JsonProperty(namespace = "address_id")
    private Integer addressId;

    private boolean active = true;
}
