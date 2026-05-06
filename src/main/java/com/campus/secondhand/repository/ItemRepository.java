package com.campus.secondhand.repository;

import com.campus.secondhand.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.math.BigDecimal;

public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> findByStatus(Integer status);

    List<Item> findByPriceGreaterThan(BigDecimal price);

    List<Item> findByCategory(String category);

    List<Item> findBySellerId(String sellerId);

    @Query("SELECT i.itemName, u.userName FROM Item i " +
            "JOIN Order o ON i.itemId = o.itemId " +
            "JOIN User u ON o.buyerId = u.userId " +
            "WHERE i.status = 1")
    List<Object[]> findSoldItemsWithBuyerName();

    @Query("SELECT COUNT(i) FROM Item i")
    Long getTotalItemCount();

    @Query("SELECT i.category, COUNT(i) FROM Item i GROUP BY i.category")
    List<Object[]> countByCategory();

    @Query("SELECT AVG(i.price) FROM Item i")
    Double getAveragePrice();

    @Query("SELECT i.sellerId, COUNT(i) FROM Item i GROUP BY i.sellerId ORDER BY COUNT(i) DESC")
    List<Object[]> findTopSeller();
}