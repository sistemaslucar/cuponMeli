package com.cupon.meli.dto;

import java.util.List;

public class CouponRequest {
    private List<String> itemIds;
    private Float amount;

    // Getters and Setters


    public List<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}