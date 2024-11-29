package org.example.nuiifo0d.dto.response;

import lombok.*;
import org.example.nuiifo0d.entity.CartItem;

import java.math.BigDecimal;

@Setter
@Getter
public class CartItemResponse {
    private Long itemId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;

    public CartItemResponse(CartItem cartItem) {
        this.itemId = cartItem.getId();
        this.name = cartItem.getItem().getName();
        this.description = cartItem.getItem().getDescription();
        this.price = cartItem.getItem().getPrice();
        this.quantity = cartItem.getQuantity();
        this.totalPrice = cartItem.getItem().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}