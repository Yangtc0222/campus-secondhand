package com.campus.secondhand.repository;

import com.campus.secondhand.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT i.itemName, u.userName, o.orderDate FROM Order o " +
            "JOIN Item i ON o.itemId = i.itemId " +
            "JOIN User u ON o.buyerId = u.userId")
    List<Object[]> findOrderDetails();

    @Query("SELECT i, o FROM Item i LEFT JOIN Order o ON i.itemId = o.itemId WHERE i.sellerId = 'u001'")
    List<Object[]> findU001ItemsWithOrder();
}