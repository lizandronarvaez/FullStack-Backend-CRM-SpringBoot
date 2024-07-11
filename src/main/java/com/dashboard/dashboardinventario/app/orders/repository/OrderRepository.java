package com.dashboard.dashboardinventario.app.orders.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.dashboard.dashboardinventario.app.clients.models.entities.ClientEntity;
import com.dashboard.dashboardinventario.app.orders.models.entities.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {

    @Query("SELECT o FROM OrderEntity o WHERE o.clientEntity= ?1")
    List<OrderEntity> findOrderByIdClient(ClientEntity clientEntity);
}
