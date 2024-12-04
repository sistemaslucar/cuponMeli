package com.cupon.meli.dto;

import java.util.List;

public class CouponResponse {

    private List<String> itemIds;
    private Float total;

    public CouponResponse(List<String> itemIds, Float total) {
        this.itemIds = itemIds;
        this.total = total;
    }

    // Getters and Setters

    public List<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
