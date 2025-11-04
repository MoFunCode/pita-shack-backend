package com.pitashack.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity  // ← CRITICAL: Tells Spring this is a database table
@Table(name = "menu_items")  // ← Table name in database
@Data  // ← Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // ← Empty constructor
@AllArgsConstructor  // ← Constructor with all fields
public class MenuItem {

    @Id  // ← Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ← Auto-increment
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be empty")// ← Cannot be null
    @Size(min = 2, max = 100, message = "Name must be between 1-100 characters")
    private String name;

    @Column(length = 500)
    @Size(max = 500)// ← Max 500 characters
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)  // ← Required, max 99999999.99
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @Column(nullable = false)
    private String category;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean isAvailable = true;  // ← Default value

    private String badge;

    @Column(updatable = false)  // ← Never changes after creation
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist  // ← Runs BEFORE first save
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate  // ← Runs BEFORE every update
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
