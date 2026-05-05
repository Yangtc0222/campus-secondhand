package com.campus.secondhand.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "buyer_id")
    private String buyerId;

    @Column(name = "order_date")
    private LocalDate orderDate;
}