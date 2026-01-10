package com.Prashant.FoodDelivery.repo;

import com.Prashant.FoodDelivery.Model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItems,Long> {

}
