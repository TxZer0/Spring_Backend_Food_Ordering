package org.example.nuiifo0d.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateQuantity {
    @Pattern(
            regexp = "^(inc|dec)$",
            message = "Có lỗi xảy ra."
    )
    private String status;
}
