package com.sivalabs.jtme;

import java.math.BigDecimal;

public class Order {
    private Long id;
    private String productCode;
    private BigDecimal price;
    private int quantity;

    public Order(Long id, String productCode, BigDecimal price, int quantity) {
        this.id = id;
        this.productCode = productCode;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
