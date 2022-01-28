package com.sprokazin.cakesShop.orders;

import com.sprokazin.cakesShop.rest.dto.order.Order;
import com.sprokazin.cakesShop.rest.dto.order.OrderForAdmin;

import java.util.List;

public interface OrderService {
    void addOrder(Order order);

    List<OrderForAdmin> getOrders();

    OrderForAdmin getOrderById(Long id);

    void changeStatus(Long id, OrderStatus orderStatus);

    void deleteOrderById(Long id);
}
