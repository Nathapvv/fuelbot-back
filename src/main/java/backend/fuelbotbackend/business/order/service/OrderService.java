package backend.fuelbotbackend.business.order.service;

import java.util.List;

import backend.fuelbotbackend.business.order.dto.CreateOrderRequest;
import backend.fuelbotbackend.business.order.dto.CreateOrderResponse;
import backend.fuelbotbackend.business.order.dto.OrderResponse;
import backend.fuelbotbackend.business.order.model.Order;
import jakarta.transaction.Transactional;

public interface OrderService {
    @Transactional
    CreateOrderResponse createCommande(CreateOrderRequest request);

    @Transactional
    void validerCommande(Long orderId);

    @Transactional
    List<OrderResponse> getOrdersByUserIdUtilisateur(Long userId) ; 
}
