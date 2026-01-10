package com.Prashant.FoodDelivery.Request;

import com.Prashant.FoodDelivery.Model.Address;
import com.Prashant.FoodDelivery.Model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;

    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
