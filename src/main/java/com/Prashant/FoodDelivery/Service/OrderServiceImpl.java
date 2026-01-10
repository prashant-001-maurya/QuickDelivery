package com.Prashant.FoodDelivery.Service;

import com.Prashant.FoodDelivery.Model.*;
import com.Prashant.FoodDelivery.Request.OrderRequest;
import com.Prashant.FoodDelivery.repo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurentService restaurentService;

    @Autowired
    private CartService cartService;

    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {
        Address shippingAddress=order.getDeliveryAddress();
        Address savedAddress=addressRepo.save(shippingAddress);
        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant=restaurentService.findRestaurantById(order.getRestaurantId());
        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("Pending");
        createdOrder.setDeliverAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());
        List<Orderitems> orderitems=new ArrayList<>();
        for (CartItems cartItems: cart.getItem()){
            Orderitems orderitem=new Orderitems();
            orderitem.setFood(cartItems.getFood());
            orderitem.setIngredients(cartItems.getIngredients());
            orderitem.setQuantity(cartItems.getQuantity());
            orderitem.setTotalPrice(cartItems.getTotalPrice());

            Orderitems savedOrderItem=orderItemRepository.save(orderitem);
            orderitems.add(savedOrderItem);
        }
        Long totalPrice=cartService.calculateCartTotal(cart);
        createdOrder.setItems(orderitems);
        createdOrder.setTotalPrice(totalPrice);
        Order savedOrder=orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);

        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order=findOrderById(orderId);
        if (orderStatus.equals("OUT FOR DELIVERY")||orderStatus.equals("Delivered")||orderStatus.equals("COMPLETED")||orderStatus.equals("PENDING")){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please Select a Valid Order Status");
    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order order=findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByRestaurantId(restaurantId);
        if (orderStatus!=null){
            orders=orders.stream().filter(order ->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order>optionalOrder=orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()){
            throw new Exception("Order not found");
        }
        return optionalOrder.get();
    }
}
