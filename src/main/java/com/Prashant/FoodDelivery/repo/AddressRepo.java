package com.Prashant.FoodDelivery.repo;

import com.Prashant.FoodDelivery.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address,Long> {
}
