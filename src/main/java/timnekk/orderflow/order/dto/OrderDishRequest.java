package timnekk.orderflow.order.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishRequest {

    private Integer id;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;

}
