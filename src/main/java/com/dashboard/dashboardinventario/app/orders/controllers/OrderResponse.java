package com.dashboard.dashboardinventario.app.orders.controllers;

import java.util.List;
import com.dashboard.dashboardinventario.app.orders.models.entities.OrderEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderResponse {
    private Integer status;
    private List<OrderEntity> orderList;
    private String message;
}
