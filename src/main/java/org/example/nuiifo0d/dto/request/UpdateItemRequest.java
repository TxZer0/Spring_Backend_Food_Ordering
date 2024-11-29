package org.example.nuiifo0d.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateItemRequest {
    @Size(max = 100, message = "Tên không được quá 100 kí tự.")
    private String name;

    @Size(max = 255, message = "Mô tả không được quá 255 kí tự.")
    private String description;

    @Size(max = 50, message = "Loại không được quá 50 kí tự.")
    private String category;

    @Min(value = 0, message = "Giá phải lớn hơn hoặc bằng 0.")
    private BigDecimal price;

    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0.")
    private Integer quantity;
}
