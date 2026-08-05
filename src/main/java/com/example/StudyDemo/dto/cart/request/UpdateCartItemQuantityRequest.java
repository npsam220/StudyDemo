package com.example.StudyDemo.dto.cart.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateCartItemQuantityRequest {

    @NotNull(message = "商品數量不可為空")
    @Positive(message = "商品數量必須大於 0")
    private Integer quantity;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}