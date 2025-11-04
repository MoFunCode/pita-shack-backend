package com.pitashack.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore // Prevent infinite recursion when serializing
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    @NotBlank(message = "Item name is required")
    private String itemName; // Store name at time of order

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice; // Store price at time of order

    @Column(nullable = false)
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Column(length = 500)
    private String customizations; // e.g., "No onions, extra sauce"

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Subtotal is required")
    private BigDecimal subtotal; // unitPrice * quantity
}
