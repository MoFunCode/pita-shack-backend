package com.pitashack.restaurant.controller;

import com.pitashack.restaurant.model.MenuItem;
import com.pitashack.restaurant.repository.MenuItemRepository;
import com.pitashack.restaurant.service.MenuItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menuitems")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getMenuItemsByCategory(@PathVariable String category) {
        return menuItemService.getMenuItemsByCategory(category);
    }

    @GetMapping("/{id}")
    public MenuItem getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItem item) {
        return menuItemService.createMenuItem(item);
    }

    @PutMapping("/{id}")
    public MenuItem updateMenuItem(@PathVariable Long id, @RequestBody MenuItem item) {
        return menuItemService.updateMenuItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
    }
}