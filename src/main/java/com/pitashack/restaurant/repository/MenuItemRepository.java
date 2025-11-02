package com.pitashack.restaurant.repository;

import com.pitashack.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryAndIsAvailableTrue(String category);

// - Add method: findByCategory(String category)
//- Add method: findByIsAvailableTrue()

}
