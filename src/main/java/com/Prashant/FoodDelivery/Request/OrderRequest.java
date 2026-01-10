package com.Prashant.FoodDelivery.Request;

import com.Prashant.FoodDelivery.Model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
