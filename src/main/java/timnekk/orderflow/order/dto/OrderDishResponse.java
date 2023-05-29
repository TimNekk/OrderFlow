package timnekk.orderflow.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishResponse {

    private Integer id;
    private Integer quantity;
    private String name;

}
