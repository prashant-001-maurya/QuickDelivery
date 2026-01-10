package com.Prashant.FoodDelivery.Controller;

import com.Prashant.FoodDelivery.Model.CartItems;
import com.Prashant.FoodDelivery.Model.Order;
import com.Prashant.FoodDelivery.Model.User;
import com.Prashant.FoodDelivery.Request.AddCartItemRequest;
import com.Prashant.FoodDelivery.Request.OrderRequest;
import com.Prashant.FoodDelivery.Service.OrderService;
import com.Prashant.FoodDelivery.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(
            @RequestHeader OrderRequest request,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Order order=orderService.createOrder(request,user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @GetMapping("/order")
    public ResponseEntity<List<Order>> getOrderHistory(
            @RequestHeader OrderRequest request,
            @RequestHeader("Authorization")String jwt
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Order> orders=orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
