package com.app.ecom.dto;

import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemRequest {
    private Long productId;
    private Integer quantity;

}
