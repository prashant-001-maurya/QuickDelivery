package com.Prashant.FoodDelivery.Controller;

import com.Prashant.FoodDelivery.Model.Cart;
import com.Prashant.FoodDelivery.Model.CartItems;
import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.Request.AddCartItemRequest;
import com.Prashant.FoodDelivery.Request.UpdateCartItemRequest;
import com.Prashant.FoodDelivery.Service.CartService;
import com.Prashant.FoodDelivery.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;


    @PutMapping("/cart/add")
    public ResponseEntity<CartItems> addItemToCart(
            @RequestHeader AddCartItemRequest request,
            @RequestHeader("Authorization")String jwt
            ) throws Exception {
        CartItems cartItems=cartService.addItemToCart(request,jwt);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItems> updateCartItemQuantity(
            @RequestHeader UpdateCartItemRequest request,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        CartItems cartItems=cartService.updateCartItemQuantity(request.getCartItemId(), request.getQuantity());
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> deleteCartItemQuantity(
            @PathVariable Long id,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        Cart cart=cartService.removeItemFromCart(id,jwt);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader UpdateCartItemRequest request,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Cart cart=cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
