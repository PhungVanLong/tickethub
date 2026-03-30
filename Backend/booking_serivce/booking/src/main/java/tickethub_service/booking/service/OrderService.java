package tickethub_service.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tickethub_service.booking.dto.OrderRequest;
import tickethub_service.booking.dto.OrderResponse;
import tickethub_service.booking.entity.*;
import tickethub_service.booking.entity.enums.OrderStatus;
import tickethub_service.booking.repository.OrderRepository;
import tickethub_service.booking.repository.UserRepository;
import tickethub_service.booking.repository.TicketTierRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TicketTierRepository ticketTierRepository;
    
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order with code: {}", request.getOrderCode());
        
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Order order = Order.builder()
                .orderCode(request.getOrderCode())
                .user(user)
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(BigDecimal.ZERO)
                .finalAmount(BigDecimal.ZERO)
                .notes(request.getNotes())
                .build();
        
        order = orderRepository.save(order);
        
        final Order savedOrder = order;
        
        List<OrderItem> orderItems = request.getOrderItems().stream()
                .map(itemRequest -> createOrderItem(itemRequest, savedOrder))
                .collect(Collectors.toList());
        
        BigDecimal totalAmount = orderItems.stream()
                .map(OrderItem::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        savedOrder.setOrderItems(orderItems);
        savedOrder.setTotalAmount(totalAmount);
        savedOrder.setFinalAmount(totalAmount);
        
        order = orderRepository.save(savedOrder);
        
        log.info("Order created successfully with ID: {}", order.getId());
        return convertToResponse(order);
    }
    
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToResponse(order);
    }
    
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setOrderStatus(status);
        order = orderRepository.save(order);
        
        log.info("Order status updated to {} for order ID: {}", status, orderId);
        return convertToResponse(order);
    }
    
    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByOrderStatus(status);
        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private OrderItem createOrderItem(OrderRequest.OrderItemRequest request, Order order) {
        TicketTier ticketTier = ticketTierRepository.findById(request.getTicketTierId())
                .orElseThrow(() -> new RuntimeException("Ticket tier not found"));
        
        BigDecimal finalPrice = request.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
        
        return OrderItem.builder()
                .order(order)
                .ticketTier(ticketTier)
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .finalPrice(finalPrice)
                .build();
    }
    
    private OrderResponse convertToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .userId(order.getUser().getId())
                .orderStatus(order.getOrderStatus().name())
                .totalAmount(order.getTotalAmount())
                .discountAmount(order.getDiscountAmount())
                .finalAmount(order.getFinalAmount())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .orderItems(convertOrderItems(order.getOrderItems()))
                .payments(convertPayments(order.getPayments()))
                .build();
    }
    
    private List<OrderResponse.OrderItemResponse> convertOrderItems(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> OrderResponse.OrderItemResponse.builder()
                        .id(item.getId())
                        .ticketTierId(item.getTicketTier().getId())
                        .tierName(item.getTicketTier().getTierName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discountAmount(item.getDiscountAmount())
                        .finalPrice(item.getFinalPrice())
                        .tickets(convertTickets(item.getTickets()))
                        .build())
                .collect(Collectors.toList());
    }
    
    private List<OrderResponse.PaymentResponse> convertPayments(List<Payment> payments) {
        return payments.stream()
                .map(payment -> OrderResponse.PaymentResponse.builder()
                        .id(payment.getId())
                        .paymentCode(payment.getPaymentCode())
                        .paymentMethod(payment.getPaymentMethod())
                        .amount(payment.getAmount())
                        .paymentStatus(payment.getPaymentStatus().name())
                        .paidAt(payment.getPaidAt())
                        .build())
                .collect(Collectors.toList());
    }
    
    private List<OrderResponse.TicketResponse> convertTickets(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> OrderResponse.TicketResponse.builder()
                        .id(ticket.getId())
                        .ticketCode(ticket.getTicketCode())
                        .ticketStatus(ticket.getTicketStatus().name())
                        .seatNumber(ticket.getSeatNumber())
                        .qrCode(ticket.getQrCode())
                        .usedAt(ticket.getUsedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
