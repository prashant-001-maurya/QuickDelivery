package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.IngredientCategory;
import com.Prashant.FoodDelivery.Model.IngredientsItem;

import java.util.List;

public interface IngriedentsService {

    public IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;

    public IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;

    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) ;

    public IngredientsItem updateStock(Long id) throws Exception;


}
