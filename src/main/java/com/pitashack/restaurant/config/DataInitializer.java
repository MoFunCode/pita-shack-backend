package com.pitashack.restaurant.config;

import com.pitashack.restaurant.model.MenuItem;
import com.pitashack.restaurant.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MenuItemRepository repository;

    @Override
    public void run(String... args) {
        System.out.println("🔥 Loading test data...");

        // Plates
        repository.save(createItem(
                "Shish Kabab Plate",
                "Juicy lamb & beef skewers grilled over open flame",
                "15.99", "plates",
                "https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=600",
                "🔥 Fan Favorite"
        ));

        repository.save(createItem(
                "Chicken Shawarma Plate",
                "Slow-roasted shawarma with rice and salad",
                "14.99", "plates",
                "https://images.unsplash.com/photo-1639024471283-03518883512d?w=600",
                "⭐ Chef's Pick"
        ));

        repository.save(createItem(
                "Lamb Tikka Plate",
                "Tender lamb cubes with Mediterranean spices",
                "15.99", "plates",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                null
        ));

        // Wraps
        repository.save(createItem(
                "Chicken Kabab Wrap",
                "Fresh pita with grilled chicken",
                "12.99", "wraps",
                "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=600",
                null
        ));

        repository.save(createItem(
                "Falafel Wrap",
                "Crispy falafel with tahini sauce",
                "10.99", "wraps",
                "https://images.unsplash.com/photo-1542990253-a781e04c0082?w=600",
                null
        ));

        // Appetizers
        repository.save(createItem(
                "House Hummus",
                "Creamy chickpea dip with warm pita",
                "7.24", "appetizers",
                "https://images.unsplash.com/photo-1588137378633-dea1336ce1e2?w=600",
                null
        ));

        repository.save(createItem(
                "Baba Ghanouj",
                "Smoky roasted eggplant dip",
                "7.24", "appetizers",
                "https://images.unsplash.com/photo-1604908815604-1c0c9d3b4566?w=600",
                null
        ));

        // Drinks
        repository.save(createItem(
                "Fresh Mint Lemonade",
                "Refreshing lemonade with fresh mint",
                "3.99", "drinks",
                "https://images.unsplash.com/photo-1523677011781-c91d1bbe2f8d?w=600",
                null
        ));

        // Desserts
        repository.save(createItem(
                "Baklava",
                "Sweet phyllo pastry with honey",
                "3.99", "desserts",
                "https://images.unsplash.com/photo-1519915028121-7d3463d20b13?w=600",
                "🏠 Mom's Recipe"
        ));

        System.out.println("✅ Test data loaded! Total: " + repository.count());
    }

    private MenuItem createItem(String name, String description, String price,
                                String category, String imageUrl, String badge) {
        return new MenuItem(
                null,
                name,
                description,
                new BigDecimal(price),
                category,
                imageUrl,
                true,
                badge,
                null,
                null
        );
    }
}
