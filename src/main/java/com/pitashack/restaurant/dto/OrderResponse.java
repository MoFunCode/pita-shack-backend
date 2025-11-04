package com.pitashack.restaurant.dto;

import com.pitashack.restaurant.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String orderNumber;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String estimatedTime; // e.g., "30-45 minutes"
    private String message; // e.g., "Order received successfully!"

    public OrderResponse(String orderNumber, OrderStatus status, BigDecimal totalPrice) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.totalPrice = totalPrice;
        this.estimatedTime = calculateEstimatedTime(status);
        this.message = generateMessage(status);
    }

    private String calculateEstimatedTime(OrderStatus status) {
        return switch (status) {
            case PENDING, CONFIRMED -> "30-45 minutes";
            case PREPARING -> "20-30 minutes";
            case READY -> "Ready for pickup/delivery";
            case COMPLETED -> "Completed";
            case CANCELLED -> "Cancelled";
        };
    }

    private String generateMessage(OrderStatus status) {
        return switch (status) {
            case PENDING -> "Order received successfully! We'll confirm shortly.";
            case CONFIRMED -> "Your order has been confirmed!";
            case PREPARING -> "Your order is being prepared!";
            case READY -> "Your order is ready!";
            case COMPLETED -> "Thank you for your order!";
            case CANCELLED -> "Your order has been cancelled.";
        };
    }
}
