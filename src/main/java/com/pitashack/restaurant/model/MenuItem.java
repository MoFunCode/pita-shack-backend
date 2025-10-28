package com.pitashack.restaurant.model;

//Fields you need:
//        - id (Long) - primary key
//- name (String) - dish name
//- description (String) - full description
//- price (BigDecimal) - price (use BigDecimal for money!)
//- category (String) - "plates", "wraps", "appetizers", etc.
//- imageUrl (String) - link to photo
//- isAvailable (Boolean) - is it in stock?
//        - badge (String) - "Chef's Special", "Fan Favorite", null
//        - createdAt (LocalDateTime) - when created
//- updatedAt (LocalDateTime) - when last updated

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MenuItem {
    Long id;
    String name;
    String description;
    BigDecimal price; //price (use BigDecimal for money!)
    String category; //"plates", "wraps", "appetizers", etc.
    String imageUrl; // link to photo
    Boolean isAvailable; //  is it in stock?
    String badge; // "Chef's Special", "Fan Favorite", null
    LocalDateTime createdAt; // when created
    LocalDateTime updatedAt; // when updated



}
