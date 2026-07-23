package com.example.Order.Service.Service;

import com.example.Order.Service.Advice.ResourceNotFoundException;
import com.example.Order.Service.Client.CustomerClient;
import com.example.Order.Service.Client.CustomerResponse;
import com.example.Order.Service.Entity.OrderEntity;
import com.example.Order.Service.Entity.OrderStatus;
import com.example.Order.Service.OrderDTO.OrderRequest;
import com.example.Order.Service.OrderDTO.OrderResponse;
import com.example.Order.Service.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final KafkaTemplate<String,String> kafkaTemplate;


    public OrderResponse createOrder(OrderRequest request) {

        CustomerResponse customer =
                customerClient.getCustomer(request.getCustomerId());

        if (customer == null) {
            throw new ResourceNotFoundException("Customer is not present with id: " + request.getCustomerId());
        }

        OrderEntity order = OrderEntity.builder()
                .orderNumber("ORD-" +
                        UUID.randomUUID().toString().substring(0, 8))
                .customerId(request.getCustomerId())
                .orderType(request.getOrderType())
                .status(OrderStatus.PENDING)
                .build();

        OrderEntity savedOrder = orderRepository.save(order);

        kafkaTemplate.send(
                "provisioning-request",
                savedOrder.getOrderNumber());

        return mapToResponse(savedOrder);
    }


    public OrderResponse getOrder(String orderNumber) {

        OrderEntity order = orderRepository
                .findByOrderNumber(orderNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
    }


    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(OrderEntity order) {

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.getCustomerId())
                .orderType(order.getOrderType())
                .status(order.getStatus())
                .build();
    }

    public void updateOrderStatus(String orderNum, OrderStatus orderStatus) {
        OrderEntity order = orderRepository.findByOrderNumber(orderNum).orElseThrow(
                () -> new ResourceNotFoundException("Order not found")
        );

        order.setStatus(orderStatus);

        orderRepository.save(order);
    }
}
