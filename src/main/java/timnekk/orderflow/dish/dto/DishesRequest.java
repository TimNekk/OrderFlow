package timnekk.orderflow.dish.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishesRequest {

    @Min(value = 1, message = "Page number must be greater than or equal to 1")
    private int page;

    @Min(value = 1, message = "Page size must be greater than or equal to 1")
    @Max(value = 100, message = "Page size must be less than or equal to 100")
    private int pageSize;

}
