package com.Prashant.FoodDelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String streetAddress;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
}

