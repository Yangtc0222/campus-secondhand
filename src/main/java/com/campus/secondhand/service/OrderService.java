package com.campus.secondhand.service;

import com.campus.secondhand.entity.Item;
import com.campus.secondhand.entity.Order;
import com.campus.secondhand.repository.ItemRepository;
import com.campus.secondhand.repository.OrderRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class OrderService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public boolean buyItem(String itemId, String buyerId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if (item == null || item.getStatus() == 1) {
            return false;
        }

        item.setStatus(1);
        itemRepository.save(item);

        Order order = new Order();
        order.setItemId(itemId);
        order.setBuyerId(buyerId);
        order.setOrderDate(LocalDate.now());
        orderRepository.save(order);

        return true;
    }
}