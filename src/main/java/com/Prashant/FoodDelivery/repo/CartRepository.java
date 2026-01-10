package com.Prashant.FoodDelivery.repo;

import com.Prashant.FoodDelivery.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    public Cart findByCustomerId(Long userId);
}
