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
        System.out.println("🔥 Loading Pita Shack Grill menu data...");

        // ========== PLATES ==========
        repository.save(createItem(
                "Shish Kabab Plate (1 Skewer)",
                "Juicy skewers of fresh ground lamb & beef grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "15.99", "Plate",
                "https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Shish Kabab Plate (2 Skewers)",
                "Juicy skewers of fresh ground lamb & beef grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "19.99", "Plate",
                "https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Lamb Tikka Plate (1 Skewer)",
                "Fresh lamb meat cubes skewers grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "15.99", "Plate",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Lamb Tikka Plate (2 Skewers)",
                "Fresh lamb meat cubes skewers grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "19.99", "Plate",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Tikka Plate (1 Skewer)",
                "Chicken breast cubes skewers grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "14.99", "Plate",
                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Tikka Plate (2 Skewers)",
                "Chicken breast cubes skewers grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "18.99", "Plate",
                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Kabab Plate (1 Skewer)",
                "Juicy skewers of ground chicken grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "14.99", "Plate",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Kabab Plate (2 Skewers)",
                "Juicy skewers of ground chicken grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "18.99", "Plate",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Lamb Delight",
                "Six pieces of lamb fat and tender meat rolled together grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "15.99", "Plate",
                "https://images.unsplash.com/photo-1544025162-d76694265947?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Lamb Chops (Ribs) Plate",
                "Four medium-sized lamb chops grilled over charcoal, served with basmati rice, salad, grilled veggies, pita and hummus.",
                "29.99", "Plate",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Mix Grill Plate",
                "Two assorted skewers (chicken, lamb or beef) grilled over charcoal, served with basmati rice, salad, grilled veggies, pita and hummus.",
                "19.99", "Plate",
                "https://images.unsplash.com/photo-1544025162-d76694265947?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Kumpir Plate",
                "Creamy baked potato mixed with cheese and butter, topped with vegetable toppings.",
                "9.99", "Plate",
                "https://images.unsplash.com/photo-1568205261359-fc08af8cced4?w=600",
                "Vegetarian"
        ));

        repository.save(createItem(
                "Roasted Vegetables Plate",
                "Seasonal vegetables grilled on charcoal with basmati rice, salad, grilled veggies, pita and hummus.",
                "13.99", "Plate",
                "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Falafel Plate",
                "Crispy six piece falafel, grilled over charcoal, served with basmati rice, salad, grilled veggies, pita, hummus and sauces.",
                "15.99", "Plate",
                "https://images.unsplash.com/photo-1542990253-a781e04c0082?w=600",
                "Vegan"
        ));

        // ========== WRAPS ==========
        repository.save(createItem(
                "Shish Kabab Wrap",
                "Pita filled with a shish kabab skewer, salad, grilled veggies, pickles & red sauce dip.",
                "13.99", "Wrap",
                "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Lamb Tikka Wrap",
                "Pita filled with lamb cubes, salad, grilled veggies, pickles & red sauce dip.",
                "13.99", "Wrap",
                "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Kabab Wrap",
                "Pita filled with a chicken kabab skewer, salad, grilled veggies, pickles & garlic sauce dip.",
                "12.99", "Wrap",
                "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Tikka Wrap",
                "Pita filled with chicken breast cubes, salad, grilled veggies, pickles & garlic sauce dip.",
                "12.99", "Wrap",
                "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Lamp Delight Wrap",
                "Pita filled with lamb fat & tender meat rolls, salad, grilled veggies, pickles & sauces.",
                "13.99", "Wrap",
                "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=600",
                "Halal"
        ));

        // ========== SIGNATURE TRAYS ==========
        repository.save(createItem(
                "Sharing Tray",
                "2 Shish Kabab, 2 Chicken Kabab, 2 Lamb Tikka, 2 Chicken Tikka skewers with rice, salad, fire-grilled veggies, pita and hummus.",
                "74.99", "Signature Tray",
                "https://images.unsplash.com/photo-1544025162-d76694265947?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Family Gathering Tray",
                "4 each of Shish Kabab, Chicken Kabab, Lamb Tikka and Chicken Tikka, grilled over charcoal, served with rice, salad, fire-grilled veggies, pita, hummus and baba ghanouj.",
                "149.99", "Signature Tray",
                "https://images.unsplash.com/photo-1544025162-d76694265947?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Ultimate Experience Tray",
                "6 each of Shish Kabab, Chicken Kabab, Lamb Tikka and Chicken Tikka skewers with rice, salad, fire-grilled veggies, pita, hummus, baba ghanouj and tabouleh.",
                "224.99", "Signature Tray",
                "https://images.unsplash.com/photo-1544025162-d76694265947?w=600",
                "Halal"
        ));

        // ========== APPETIZERS ==========
        repository.save(createItem(
                "Hummus",
                "8 oz house-made hummus with olive oil and spices, grilled over charcoal, served with pita.",
                "7.24", "Appetizer",
                "https://images.unsplash.com/photo-1588137378633-dea1336ce1e2?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Firey Walnut Dip",
                "8 oz dip of roasted red peppers, walnuts, olive oil & pomegranate molasses, grilled over charcoal, served with pita.",
                "7.24", "Appetizer",
                "https://images.unsplash.com/photo-1541529086526-db283c563270?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Baba Ghanouj",
                "8 oz smoky eggplant dip with tahini, garlic, lemon juice & olive oil, grilled over charcoal, served with pita.",
                "7.24", "Appetizer",
                "https://images.unsplash.com/photo-1604908815604-1c0c9d3b4566?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Tabouleh",
                "8 oz salad of parsley, bulgur, tomatoes, mint & onion with olive oil & lemon.",
                "7.24", "Appetizer",
                "https://images.unsplash.com/photo-1541519920270-b2d1ecf51b4b?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Dolma",
                "8 pieces of grape leaves stuffed with rice, herbs & spices.",
                "7.24", "Appetizer",
                "https://images.unsplash.com/photo-1569058242253-92a9c755a0ec?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Mix Dip Plate",
                "Sampler of dips including hummus, baba ghanouj, tabouleh & fiery walnut dip with pita.",
                "18.99", "Appetizer",
                "https://images.unsplash.com/photo-1541529086526-db283c563270?w=600",
                "Vegan"
        ));

        // ========== SIDES ==========
        repository.save(createItem(
                "Shish Kabab Side",
                "One shish kabab skewer with basmati rice & red sauce.",
                "7.99", "Side",
                "https://images.unsplash.com/photo-1529692236671-f1f6cf9683ba?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Lamb Tikka Side",
                "One lamb tikka skewer with basmati rice & red sauce.",
                "7.99", "Side",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Kabab Side",
                "One chicken kabab skewer with basmati rice & garlic sauce.",
                "6.99", "Side",
                "https://images.unsplash.com/photo-1603360946369-dc9bb6258143?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Chicken Tikka Side",
                "One chicken breast cube skewer with basmati rice & garlic sauce.",
                "6.99", "Side",
                "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=600",
                "Halal"
        ));

        repository.save(createItem(
                "Roasted Veggies Side",
                "Skewer of grilled vegetables with basmati rice & red sauce.",
                "4.99", "Side",
                "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Hummus Side",
                "4 oz hummus grilled over charcoal, served with pita.",
                "3.99", "Side",
                "https://images.unsplash.com/photo-1588137378633-dea1336ce1e2?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Baba-Ghanouj Side",
                "4 oz baba ghanouj grilled over charcoal, served with pita.",
                "3.99", "Side",
                "https://images.unsplash.com/photo-1604908815604-1c0c9d3b4566?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Firey Walnut Side",
                "4 oz fiery walnut dip grilled over charcoal, served with pita.",
                "3.99", "Side",
                "https://images.unsplash.com/photo-1541529086526-db283c563270?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Tabouleh Side",
                "4 oz tabouleh.",
                "3.99", "Side",
                "https://images.unsplash.com/photo-1541519920270-b2d1ecf51b4b?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Dolma 3 Pcs Side",
                "3 grape leaves stuffed with rice & herbs.",
                "3.99", "Side",
                "https://images.unsplash.com/photo-1569058242253-92a9c755a0ec?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Basmati Rice Side",
                "8 oz basmati rice with spices.",
                "4.99", "Side",
                "https://images.unsplash.com/photo-1516684732162-798a0062be99?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Pita Bread Side",
                "Two fresh-from-the-oven pita breads.",
                "1.99", "Side",
                "https://images.unsplash.com/photo-1600788907416-456578634209?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Garlic Sauce Side",
                "Side portion of garlic sauce.",
                "0.99", "Side",
                "https://images.unsplash.com/photo-1472476443507-c7a5948772fc?w=600",
                "Vegetarian"
        ));

        repository.save(createItem(
                "Green Sauce Side",
                "Side portion of green jalapeño sauce.",
                "0.99", "Side",
                "https://images.unsplash.com/photo-1472476443507-c7a5948772fc?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Red Sauce Side",
                "Side portion of red hot sauce.",
                "0.99", "Side",
                "https://images.unsplash.com/photo-1472476443507-c7a5948772fc?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Falafel Side",
                "3 pieces of crispy falafel.",
                "3.99", "Side",
                "https://images.unsplash.com/photo-1542990253-a781e04c0082?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Iraqi Lentil Soup",
                "8 oz traditional Iraqi lentil soup.",
                "4.99", "Side",
                "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=600",
                "Vegan"
        ));

        // ========== DRINKS ==========
        repository.save(createItem(
                "Sparkling Water",
                "Sparkling water bottle (20 fl oz).",
                "3.49", "Drink",
                "https://images.unsplash.com/photo-1523677011781-c91d1bbe2f8d?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Coke",
                "Coca-Cola 16.9 fl oz.",
                "2.99", "Drink",
                "https://images.unsplash.com/photo-1554866585-cd94860890b7?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Sprite",
                "Sprite 16.9 fl oz.",
                "2.99", "Drink",
                "https://images.unsplash.com/photo-1625772299848-391b6a87d7b3?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Ozarka Water",
                "Ozarka bottled water 16.9 fl oz.",
                "1.99", "Drink",
                "https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Dr. Pepper",
                "Dr. Pepper 16.9 fl oz.",
                "2.99", "Drink",
                "https://images.unsplash.com/photo-1629203851122-3726ecdf080e?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Yogurt Drink",
                "Traditional yogurt-based drink.",
                "3.99", "Drink",
                "https://images.unsplash.com/photo-1563227812-0ea4c22e6cc8?w=600",
                "Vegetarian"
        ));

        repository.save(createItem(
                "Pepsi",
                "Pepsi 16.9 fl oz.",
                "2.99", "Drink",
                "https://images.unsplash.com/photo-1629203849959-1030a13b0ec6?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Crush",
                "Crush soda 16.9 fl oz.",
                "2.99", "Drink",
                "https://images.unsplash.com/photo-1625772299848-391b6a87d7b3?w=600",
                "Vegan"
        ));

        repository.save(createItem(
                "Starry",
                "Starry beverage 16.9 fl oz.",
                "2.99", "Drink",
                "https://images.unsplash.com/photo-1625772299848-391b6a87d7b3?w=600",
                "Vegan"
        ));

        // ========== DESSERTS ==========
        repository.save(createItem(
                "Baklava",
                "Layered filo pastry with nuts and honey syrup.",
                "2.99", "Dessert",
                "https://images.unsplash.com/photo-1519915028121-7d3463d20b13?w=600",
                "Vegan"
        ));

        System.out.println("✅ Pita Shack Grill menu loaded! Total items: " + repository.count());
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
