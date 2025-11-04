package com.pitashack.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Column(nullable = false)
    @NotBlank(message = "Customer phone is required")
    private String customerPhone;

    @Column(nullable = false)
    @NotBlank(message = "Customer email is required")
    @Email(message = "Invalid email format")
    private String customerEmail;

    @Column(nullable = false)
    @NotBlank(message = "Order type is required")
    private String orderType; // "delivery" or "pickup"

    private String deliveryAddress; // nullable for pickup orders

    @Column(length = 500)
    private String specialInstructions;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Subtotal is required")
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Tax is required")
    private BigDecimal tax;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Delivery fee is required")
    private BigDecimal deliveryFee;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Total price is required")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}