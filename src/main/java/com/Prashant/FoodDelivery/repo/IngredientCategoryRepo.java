package com.Prashant.FoodDelivery.repo;

import com.Prashant.FoodDelivery.Model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepo extends JpaRepository<IngredientCategory,Long> {

    List<IngredientCategory>findByRestaurantId(Long id);
}
