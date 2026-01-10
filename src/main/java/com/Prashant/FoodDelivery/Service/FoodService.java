package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.Category;
import com.Prashant.FoodDelivery.Model.Food;
import com.Prashant.FoodDelivery.Model.Restaurant;
import com.Prashant.FoodDelivery.Request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest re, Category category, Restaurant restaurant);

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantFood(Long restaurantId,boolean isVegetarian,boolean isNonVeg,boolean isSeasonal,String foodCategory);

    public List<Food> searchFood(String keyword);
    public Food findFoodById(Long foodId)throws Exception;

    public Food updateAvailabilityStatus(Long foodId)throws Exception;

    List<Food> getRestaurantsFood(Long restaurantId, boolean vegetarian, boolean nonveg, boolean seasonal, String foodCategory);
}
