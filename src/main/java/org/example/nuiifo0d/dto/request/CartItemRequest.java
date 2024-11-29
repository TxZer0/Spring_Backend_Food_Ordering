package org.example.nuiifo0d.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemRequest {
    @NotNull(message = "Không tìm thấy sản phẩm.")
    @Positive(message = "Không tìm thấy sản phẩm.")
    private Long itemId;

    @NotNull(message = "Số lượng không được để trống.")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0.")
    private Integer quantity;

}
