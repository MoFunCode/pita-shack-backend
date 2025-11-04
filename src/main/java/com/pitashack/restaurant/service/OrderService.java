package com.pitashack.restaurant.service;

import com.pitashack.restaurant.dto.OrderItemDTO;
import com.pitashack.restaurant.dto.OrderRequest;
import com.pitashack.restaurant.dto.OrderResponse;
import com.pitashack.restaurant.exception.ResourceNotFoundException;
import com.pitashack.restaurant.model.MenuItem;
import com.pitashack.restaurant.model.Order;
import com.pitashack.restaurant.model.OrderItem;
import com.pitashack.restaurant.model.OrderStatus;
import com.pitashack.restaurant.repository.MenuItemRepository;
import com.pitashack.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    private static final BigDecimal TAX_RATE = new BigDecimal("0.08"); // 8% tax
    private static final BigDecimal DELIVERY_FEE = new BigDecimal("5.00");
    private static final BigDecimal FREE_DELIVERY_THRESHOLD = new BigDecimal("50.00");

    public OrderService(OrderRepository orderRepository, MenuItemRepository menuItemRepository) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        // Validate delivery address if order type is delivery
        if ("delivery".equalsIgnoreCase(request.getOrderType()) &&
                (request.getDeliveryAddress() == null || request.getDeliveryAddress().isBlank())) {
            throw new IllegalArgumentException("Delivery address is required for delivery orders");
        }

        // Create new order
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setOrderType(request.getOrderType());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setSpecialInstructions(request.getSpecialInstructions());
        order.setStatus(OrderStatus.PENDING);

        // Create order items and calculate subtotal
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Menu item with id " + itemDTO.getMenuItemId() + " not found"));

            // Check if item is available
            if (!menuItem.getIsAvailable()) {
                throw new IllegalArgumentException(
                        "Menu item '" + menuItem.getName() + "' is not available");
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setItemName(menuItem.getName()); // Store name at time of order
            orderItem.setUnitPrice(menuItem.getPrice()); // Store price at time of order
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setCustomizations(itemDTO.getCustomizations());

            // Calculate item subtotal
            BigDecimal itemSubtotal = menuItem.getPrice()
                    .multiply(new BigDecimal(itemDTO.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);
            orderItem.setSubtotal(itemSubtotal);

            orderItems.add(orderItem);
            subtotal = subtotal.add(itemSubtotal);
        }

        // Calculate tax
        BigDecimal tax = subtotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);

        // Calculate delivery fee (free for orders over threshold or pickup orders)
        BigDecimal deliveryFee = BigDecimal.ZERO;
        if ("delivery".equalsIgnoreCase(request.getOrderType())) {
            deliveryFee = subtotal.compareTo(FREE_DELIVERY_THRESHOLD) >= 0
                    ? BigDecimal.ZERO
                    : DELIVERY_FEE;
        }

        // Calculate total
        BigDecimal totalPrice = subtotal.add(tax).add(deliveryFee).setScale(2, RoundingMode.HALF_UP);

        // Set order totals
        order.setSubtotal(subtotal);
        order.setTax(tax);
        order.setDeliveryFee(deliveryFee);
        order.setTotalPrice(totalPrice);
        order.setItems(orderItems);

        // Save order (cascade will save order items)
        Order savedOrder = orderRepository.save(order);

        // Return response
        return new OrderResponse(
                savedOrder.getOrderNumber(),
                savedOrder.getStatus(),
                savedOrder.getTotalPrice()
        );
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));
    }

    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order with order number " + orderNumber + " not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + id + " not found"));

        order.setStatus(status);

        // Set completed time if order is completed
        if (status == OrderStatus.COMPLETED) {
            order.setCompletedAt(LocalDateTime.now());
        }

        Order updatedOrder = orderRepository.save(order);

        return new OrderResponse(
                updatedOrder.getOrderNumber(),
                updatedOrder.getStatus(),
                updatedOrder.getTotalPrice()
        );
    }

    private String generateOrderNumber() {
        // Generate order number like: ORD-20231104-ABC123
        String timestamp = LocalDateTime.now().toString().substring(0, 10).replace("-", "");
        String uuid = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORD-" + timestamp + "-" + uuid;
    }
}
