package timnekk.orderflow.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private String specialRequests;

    @NotEmpty(message = "Dishes must not be empty")
    @Valid
    private Set<OrderDishRequest> dishes;

}
