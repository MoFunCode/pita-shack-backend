package com.pitashack.restaurant.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)  // ← Cannot be null
    private String name;

    @Column(length = 500)  // ← Max 500 characters
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)  // ← Required, max 99999999.99
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
