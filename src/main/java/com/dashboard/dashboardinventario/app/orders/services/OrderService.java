package com.dashboard.dashboardinventario.app.orders.services;

import java.util.List;

import com.dashboard.dashboardinventario.app.orders.controllers.OrderResponse;
import com.dashboard.dashboardinventario.app.orders.models.dto.OrderDto;
import com.dashboard.dashboardinventario.app.orders.models.entities.OrderEntity;

public interface OrderService {

    OrderEntity createOrder(OrderDto orderDto);

    List<OrderEntity> getAllOrders();

    OrderResponse getOrderById(Integer id);
}
