package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.Restaurant;
import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.Request.CreateRestaurantRequest;
import com.Prashant.FoodDelivery.dto.RestaurantDTO;

import java.util.List;

public interface RestaurentService {

    public Restaurant createRestaurant(CreateRestaurantRequest req,User user);

    public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedRestaurant)
        throws Exception;

    public Restaurant deleteRestaurant(Long restaurantId)
        throws Exception;

    public List<Restaurant>getAllRestaurant();
    public List<Restaurant>searchRestaurant(String keyword);
    public Restaurant findRestaurantById(Long id)
        throws Exception;
    public Restaurant getRestaurantByUserId(Long userId)
        throws Exception;

    public RestaurantDTO addToFavorites(Long restaurantId,User user)
        throws Exception;
    public Restaurant updateRestaurantStatus(Long id)
        throws Exception;
}
