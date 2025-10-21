package com.spring.project.microservices.orderservice.service;

import com.spring.project.microservices.orderservice.client.ProductClient;
import com.spring.project.microservices.orderservice.client.UserClient;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderItemRequestDto;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderItemResponseDto;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderRequestDto;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderResponseDto;
import com.spring.project.microservices.orderservice.dto.ProductResponseDto;
import com.spring.project.microservices.orderservice.dto.UserResponseDto;
import com.spring.project.microservices.orderservice.exception.ResourceNotFoundException;
import com.spring.project.microservices.orderservice.mapper.OrderItemMapper;
import com.spring.project.microservices.orderservice.mapper.OrderMapper;
import com.spring.project.microservices.orderservice.model.Order;
import com.spring.project.microservices.orderservice.model.OrderItem;
import com.spring.project.microservices.orderservice.repository.OrderItemRepository;
import com.spring.project.microservices.orderservice.repository.OrderRepository;
import feign.FeignException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final ProductClient productClient;
    private final UserClient userClient;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService, ProductClient productClient, UserClient userClient, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.productClient = productClient;
        this.userClient = userClient;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
    }

    public ResponseEntity<?> saveOrder(OrderRequestDto request) {
        // 1️⃣ Xác thực user từ User Service
//        UserResponseDto userResponse = userClient.getUser(request.getUserId()).getBody();

        // 2️⃣ Tạo order và danh sách orderItems
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus("PENDING");

        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDto itemReq : request.getItems()) {
            ProductResponseDto product = productClient.getProduct(itemReq.getProductId());

            double subtotal = product.getPrice() * itemReq.getQuantity();
            total += subtotal;

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice());
            item.setSubtotal(subtotal);
            item.setOrder(order); // rất quan trọng để mapping ngược lại
            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        // 3️⃣ Chỉ cần lưu order, JPA sẽ tự cascade lưu các order_items
        Order savedOrder = orderRepository.save(order);

        return ResponseEntity.ok(Map.of(
                "orderId", savedOrder.getId(),
                "totalPrice", savedOrder.getTotalPrice(),
                "status", savedOrder.getStatus(),
                "items", orderItemMapper.toResponseDtoList(savedOrder.getItems())
        ));
    }

    //    public ResponseEntity<?> getOrderWithProduct(Long orderId) {
//        try {
//            Order order = orderRepository.findById(orderId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
//            OrderItem orderItem = orderItemRepository.findByOrderId(orderId);
//
//
//            UserResponseDto user = userClient.getUser(order.getUserId()).getBody();
//            ProductResponseDto product = productClient.getProduct(orderItem.getProductId()).getBody();
//            // tiếp tục logic
//
//            return ResponseEntity.ok("Thông tin order kèm product: " + product.getName());
//        } catch (FeignException ex) {
//            // Đọc lỗi từ product-service
//            String errorJson = ex.contentUTF8();
//
//            // Trả lại nguyên vẹn lỗi này cho client gọi order-service
//            return ResponseEntity
//                    .status(ex.status()) // status code từ product-service
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(errorJson); // Trả lại JSON string
//        }
//    }

    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));

        UserResponseDto userResponseDto = userClient.getUser(order.getUserId()).getBody();
        List<OrderItemResponseDto> orderItem = orderItemService.getOrderItemByOrderId(id);

        OrderResponseDto orderResponseDto = orderMapper.toResponseDto(order);
        orderResponseDto.setUserName(userResponseDto.getUsername());
        orderResponseDto.setItems(orderItem);
        return orderResponseDto;
    }
}
