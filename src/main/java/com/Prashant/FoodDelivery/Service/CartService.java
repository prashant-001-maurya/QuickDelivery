package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.Cart;
import com.Prashant.FoodDelivery.Model.CartItems;
import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.Request.AddCartItemRequest;

public interface CartService {

    public CartItems addItemToCart(AddCartItemRequest req, String jwt)throws Exception;

    public CartItems updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;

    public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;

    public Long calculateCartTotal(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    public Cart clearCart(Long userId)throws Exception;
}
