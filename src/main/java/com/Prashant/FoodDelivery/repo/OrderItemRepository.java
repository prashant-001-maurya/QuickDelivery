package com.Prashant.FoodDelivery.repo;

import com.Prashant.FoodDelivery.Model.Order;
import com.Prashant.FoodDelivery.Model.Orderitems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Orderitems,Long> {
}
