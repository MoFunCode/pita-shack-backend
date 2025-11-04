package com.pitashack.restaurant.service;

import com.pitashack.restaurant.exception.ResourceNotFoundException;
import com.pitashack.restaurant.model.MenuItem;
import com.pitashack.restaurant.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    // Read all
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    // Read by category
    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItemRepository.findByCategoryAndIsAvailableTrue(category);
    }

    // Read by ID
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item with id " + id + " not found"));
    }

    // Create
    public MenuItem createMenuItem(MenuItem item) {
        return menuItemRepository.save(item);
    }

    // Update
    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
        MenuItem existingItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item with id " + id + " not found"));

        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setImageUrl(updatedItem.getImageUrl());
        existingItem.setIsAvailable(updatedItem.getIsAvailable());
        existingItem.setBadge(updatedItem.getBadge());

        return menuItemRepository.save(existingItem);
    }

    // Delete
    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Menu item with id " + id + " not found");
        }
        menuItemRepository.deleteById(id);
    }
}