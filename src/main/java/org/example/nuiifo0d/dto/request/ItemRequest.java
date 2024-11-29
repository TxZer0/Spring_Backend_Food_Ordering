package org.example.nuiifo0d.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemRequest {
    @NotNull(message = "Tên không được để trống.")
    @Size(min = 1, max = 100, message = "Tên phải có từ 1 đến 100 kí tự.")
    private String name;

    @Size(max = 500, message = "Mô tả phải ít hơn 500 kí tự.")
    private String description;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", message = "Giá phải lớn hơn hoặc bằng 0.")
    private BigDecimal price;

    @Size(max = 50, message = "Loại không được quá 50 kí tự.")
    private String category;

    @Min(value = 1, message = "Số lượng phải lớn hơn 0.")
    private Integer quantity;
}
