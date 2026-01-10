package com.Prashant.FoodDelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRestaurantDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> images;
}
