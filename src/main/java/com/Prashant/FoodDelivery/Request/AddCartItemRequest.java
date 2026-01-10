package com.Prashant.FoodDelivery.Request;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {
    private Long foodId;
    private int Quantity;
    private List<String> ingredient;
}
