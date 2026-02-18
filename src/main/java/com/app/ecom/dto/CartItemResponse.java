package com.app.ecom.dto;

import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CartItemResponse {

    private Long Id;
    private User user;
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime creationDate;
    private LocalDateTime updatedAt;
}
