package com.dashboard.dashboardinventario.app.orders.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;
import com.dashboard.dashboardinventario.app.clients.repository.ClientsRepository;
import com.dashboard.dashboardinventario.app.orders.controllers.OrderResponse;
import com.dashboard.dashboardinventario.app.orders.models.dto.OrderDto;
import com.dashboard.dashboardinventario.app.orders.models.entities.OrderEntity;
import com.dashboard.dashboardinventario.app.orders.models.entities.OrderItemEntity;
import com.dashboard.dashboardinventario.app.orders.repository.OrderItemRepository;
import com.dashboard.dashboardinventario.app.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ClientsRepository clientsRepository;

    @Transactional
    @Override
    public OrderEntity createOrder(OrderDto orderDto) {

        // Creamos la orden
        final OrderEntity orderEntity = OrderEntity
                .builder()
                .clientEntity(orderDto.getClient())
                .total(Double.parseDouble(orderDto.getTotal()))
                .build();

        // Guardar la orden principal para obtener el ID generado
        OrderEntity saveOrderEntity = orderRepository.save(orderEntity);
        // Obtener la lista de productos
        List<OrderItemEntity> orderDetails = new ArrayList<>();
        orderDto.getOrder().forEach(product -> {
            Optional<OrderItemEntity> existingOrderItem = orderItemRepository.findById(product.getId());
            if (existingOrderItem.isEmpty()) {
                // Crear un detalle de pedido
                OrderItemEntity orderItemEntity = OrderItemEntity
                        .builder()
                        .order(saveOrderEntity)
                        .product(product)
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .build();
                orderDetails.add(orderItemEntity);
            } else {
                OrderItemEntity existingOrder = existingOrderItem.get();
                orderDetails.add(existingOrder);
            }
        });

        // Guardar los detalles de la orden en la base de datos
        orderItemRepository.saveAll(orderDetails);
        // Asignar los detalles de la orden a la orden principal
        orderEntity.setDetails(orderDetails);
        // Actualizar la orden principal en la base de datos para incluir los detalles
        orderRepository.save(orderEntity);
        return orderEntity;
    }

    // Obtener todas las ordenes
    @Transactional(readOnly = true)
    @Override
    public List<OrderEntity> getAllOrders() {
        return (List<OrderEntity>) orderRepository.findAll();
    }

    // Obtener las ordenes por el id del usuario
    @Transactional(readOnly = true)
    @Override
    public OrderResponse getOrderById(Integer id) {
        Optional<ClientEntity> clientEntity = clientsRepository.findById(id);
        List<OrderEntity> listOrderClient = orderRepository.findOrderByIdClient(clientEntity.get());

        if (listOrderClient.isEmpty()) {
            return OrderResponse
                    .builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .orderList(listOrderClient)
                    .message("El cliente no tiene pedidos realizados")
                    .build();
        }
        return OrderResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message("Pedidos de el cliente")
                .orderList(listOrderClient).build();
    }

}
