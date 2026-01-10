package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.Address;
import com.Prashant.FoodDelivery.Model.Restaurant;
import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.Request.CreateRestaurantRequest;
import com.Prashant.FoodDelivery.dto.RestaurantDTO;
import com.Prashant.FoodDelivery.repo.AddressRepo;
import com.Prashant.FoodDelivery.repo.RestaurantRepo;
import com.Prashant.FoodDelivery.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurentService{

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address=addressRepo.save(req.getAddress());
        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
       Restaurant restaurant=findRestaurantById(restaurantId);
       if(restaurant.getCuisineType()!=null){
           restaurant.setCuisineType(updatedRestaurant.getCuisineType());
       }
       if (restaurant.getDescription()!=null){
           restaurant.setDescription(updatedRestaurant.getDescription());
       }
       if (restaurant.getName()!=null){
           restaurant.setName(updatedRestaurant.getName());
       }
       return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        restaurantRepo.delete(restaurant);
        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String Keyword) {
        return restaurantRepo.findBySearchQuery(Keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Restaurant not Found with id"+id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepo.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("restaurant not found with owner id"+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDTO addToFavorites(Long restaurantId, User user) throws Exception {

        Restaurant restaurant = findRestaurantById(restaurantId);

        if (user.getFavorites().contains(restaurant)) {
            user.getFavorites().remove(restaurant);
        } else {
            user.getFavorites().add(restaurant);
        }

        userRepository.save(user);

        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setTitle(restaurant.getName());
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());

        return dto;
    }


    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepo.save(restaurant);
    }
}
