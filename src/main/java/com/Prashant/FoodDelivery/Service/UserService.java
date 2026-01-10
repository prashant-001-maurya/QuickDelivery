package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

public interface UserService {

    public User findUserByJwtToken(String jwt)
        throws Exception;

    public User findUserByEmail(String email)
        throws Exception;

    public UserResponseDTO mapToUserResponseDTO(User user);
}
