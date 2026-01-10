package com.Prashant.FoodDelivery.repo;

import com.Prashant.FoodDelivery.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String username);
}
