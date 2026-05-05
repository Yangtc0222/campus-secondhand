package com.campus.secondhand.entity;

import javax.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_name")
    private String itemName;

    private String category;
    private BigDecimal price;
    private Integer status;

    @Column(name = "seller_id")
    private String sellerId;
}