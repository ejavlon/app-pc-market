package com.example.apppcmarket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @NotNull(message = "Ismi kiritilmadi")
    private String firstName;

    @NotNull(message = "Familiyasi kiritilmadi")
    private String lastName;

    @NotNull(message = "Telefon nomeri kiritilmadi")
    private String phoneNumber;

    @NotNull(message = "Address id kiritilmadi")
    private Integer address_id;
}
