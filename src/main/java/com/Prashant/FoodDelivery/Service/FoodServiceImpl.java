package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.Category;
import com.Prashant.FoodDelivery.Model.Food;
import com.Prashant.FoodDelivery.Model.Restaurant;
import com.Prashant.FoodDelivery.Request.CreateFoodRequest;
import com.Prashant.FoodDelivery.repo.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();

        food.setName(req.getName());
        food.setDescription(req.getDescription());
        food.setPrice(req.getPrice());
        food.setImages(req.getImages());
        food.setIngredients(req.getIngredients());
        food.setVegetarian(req.isVegetarian());
        food.setSeasonal(req.isSessional());
        food.setAvailable(true);

        food.setFoodCategory(category);
        food.setRestaurant(restaurant);

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public List<Food> getRestaurantFood(
            Long restaurantId,
            boolean isVegetarian,
            boolean isNonVeg,
            boolean isSeasonal,
            String foodCategory) {

        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

        if (isVegetarian) {
            foods = foods.stream()
                    .filter(Food::isVegetarian)
                    .collect(Collectors.toList());
        }

        if (isNonVeg) {
            foods = foods.stream()
                    .filter(food -> !food.isVegetarian())
                    .collect(Collectors.toList());
        }

        if (isSeasonal) {
            foods = foods.stream()
                    .filter(Food::isSeasonal)
                    .collect(Collectors.toList());
        }

        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = foods.stream()
                    .filter(food ->
                            food.getFoodCategory() != null &&
                                    food.getFoodCategory().getName().equalsIgnoreCase(foodCategory)
                    )
                    .collect(Collectors.toList());
        }

        return foods;
    }

    // 🔑 REQUIRED because it exists in the interface
    @Override
    public List<Food> getRestaurantsFood(
            Long restaurantId,
            boolean vegetarian,
            boolean nonveg,
            boolean seasonal,
            String foodCategory) {

        // delegate to the main method
        return getRestaurantFood(
                restaurantId,
                vegetarian,
                nonveg,
                seasonal,
                foodCategory
        );
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepository.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            throw new Exception("Food not found with id: " + foodId);
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }
}

