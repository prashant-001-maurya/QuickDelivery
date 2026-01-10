package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.config.JwtProvider;
import com.Prashant.FoodDelivery.dto.AddressDTO;
import com.Prashant.FoodDelivery.dto.FavoriteRestaurantDTO;
import com.Prashant.FoodDelivery.dto.UserResponseDTO;
import com.Prashant.FoodDelivery.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final JwtProvider jwtProvider;

    public UserServiceImp(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User Not found");
        }
        return user;
    }

    @Override
    public UserResponseDTO mapToUserResponseDTO(User user) {

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());

        // ✅ map favorites (null-safe)
        List<FavoriteRestaurantDTO> favorites =
                user.getFavorites() == null
                        ? Collections.emptyList()
                        : user.getFavorites().stream()
                        .map(restaurant -> {
                            FavoriteRestaurantDTO fav = new FavoriteRestaurantDTO();
                            fav.setId(restaurant.getId());
                            fav.setName(restaurant.getName());
                            fav.setDescription(restaurant.getDescription());
                            fav.setImages(restaurant.getImages());
                            return fav;
                        })
                        .collect(Collectors.toList());

        dto.setFavorites(favorites);

        // ✅ map user addresses (null-safe)
        List<AddressDTO> addressDTOs =
                user.getAddresses() == null
                        ? Collections.emptyList()
                        : user.getAddresses().stream()
                        .map(address -> {
                            AddressDTO addressDTO = new AddressDTO();
                            addressDTO.setStreetAddress(address.getStreetAddress());
                            addressDTO.setCity(address.getCity());
                            addressDTO.setStateProvince(address.getStateProvince());
                            addressDTO.setPostalCode(address.getPostalCode());
                            addressDTO.setCountry(address.getCountry());
                            return addressDTO;
                        })
                        .collect(Collectors.toList());

        dto.setAddresses(addressDTOs);

        return dto;
    }
}

