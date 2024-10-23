package com.example.ecommerce.services;

import com.example.ecommerce.dtos.OrderDto;
import com.example.ecommerce.exceptions.NotEnoughProductsException;
import com.example.ecommerce.exceptions.ResourceNotFoundException;
import com.example.ecommerce.models.Cart;
import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.OrderItem;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.models.enums.OrderStatus;
import com.example.ecommerce.repos.OrderItemRepo;
import com.example.ecommerce.repos.OrderRepo;
import com.example.ecommerce.repos.ProductRepo;
import com.example.ecommerce.services.iservices.ICartService;
import com.example.ecommerce.services.iservices.IOrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ICartService cartService;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private  ModelMapper modelMapper;

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepo.findById(orderId).map(this::convertToDto)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }


    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calcTotalAmount(orderItems));
        Order savedOrder = orderRepo.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDENG);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().
                map(cartItem -> {
                    Product product = cartItem.getProduct();
                    int inventory = product.getInventory();
                    if(inventory < cartItem.getQuantity()){
                        throw new NotEnoughProductsException("the is not enough quantity");
                    }
                    product.setInventory(product.getInventory()-cartItem.getQuantity());
                    productRepo.save(product);
                    return orderItemRepo.save( new OrderItem(
                            cartItem.getQuantity(),
                            cartItem.getUnitPrice(),
                            order,
                            product
                    ));
                }).toList();

    }
    private BigDecimal calcTotalAmount(List<OrderItem> orderItemList){
        return orderItemList.stream().map(item ->
                item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
        return orderRepo.findByUserId(userId).stream().map(this::convertToDto)
                .toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
