package com.example.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

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
    private Integer address_id;

    private boolean active = true;
}
