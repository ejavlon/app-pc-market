package com.company.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @NotNull(message = "Ism kiritilmadi")
    private String firstName;

    @NotNull(message = "Familiya kiritilmadi")
    private String lastName;

    @NotNull(message = "Telefon nomer kiritilmadi")
    private String phoneNumber;

    @NotNull(message = "Address id kiritilmadi")
    private Integer address_id;
}
