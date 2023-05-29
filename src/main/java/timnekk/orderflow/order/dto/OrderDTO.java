package timnekk.orderflow.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import timnekk.orderflow.order.entity.OrderStatus;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private OrderStatus status;
    private String specialRequests;
    private Set<OrderDishResponse> dishes;

}
