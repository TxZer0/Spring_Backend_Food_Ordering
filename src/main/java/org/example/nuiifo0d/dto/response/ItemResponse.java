package org.example.nuiifo0d.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.nuiifo0d.entity.Item;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String category;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.category = item.getCategory();
    }
}
