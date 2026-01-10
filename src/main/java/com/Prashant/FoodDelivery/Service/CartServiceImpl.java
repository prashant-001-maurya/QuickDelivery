package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.Cart;
import com.Prashant.FoodDelivery.Model.CartItems;
import com.Prashant.FoodDelivery.Model.Food;
import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.Request.AddCartItemRequest;
import com.Prashant.FoodDelivery.repo.CartItemRepo;
import com.Prashant.FoodDelivery.repo.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItems addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Food food=foodService.findFoodById(req.getFoodId());
        Cart cart=cartRepository.findByCustomerId(user.getId());
        for(CartItems cartItems:cart.getItem()){
            if(cartItems.getFood().equals(food)){
                int newQuantity=cartItems.getQuantity()+ req.getQuantity();
                return updateCartItemQuantity(cartItems.getId(),newQuantity);
            }
        }

        CartItems newCartItem=new CartItems();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredient());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
        CartItems savedCartItem=cartItemRepo.save(newCartItem);
        cart.getItem().add(savedCartItem);

        return savedCartItem;
    }

    @Override
    public CartItems updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItems> cartItemsOptional=cartItemRepo.findById(cartItemId);
        if(cartItemsOptional.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItems items=cartItemsOptional.get();
        items.setQuantity(quantity);
        items.setTotalPrice(items.getFood().getPrice()*quantity);

        return cartItemRepo.save(items);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Cart cart=cartRepository.findByCustomerId(user.getId());
        Optional<CartItems> cartItemsOptional=cartItemRepo.findById(cartItemId);
        if(cartItemsOptional.isEmpty()){
            throw new Exception("Cart item not found");
        }
        CartItems items=cartItemsOptional.get();
        cart.getItem().remove(items);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Cart cart) throws Exception {
        Long total=0L;
        for (CartItems cartItems:cart.getItem()){
            total+=cartItems.getFood().getPrice()*cartItems.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
    Optional<Cart> optionalCart=cartRepository.findById(id);
    if(optionalCart.isEmpty()){
        throw new Exception("Cart not Found with id"+id);
    }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
//        User user=userService.findUserByJwtToken(jwt);
        Cart cart= cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotal(cart));
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
//        User user=userService.findUserByJwtToken(userId);
        Cart cart=findCartById(userId);
        cart.getItem().clear();
        return cartRepository.save(cart);
    }
}
