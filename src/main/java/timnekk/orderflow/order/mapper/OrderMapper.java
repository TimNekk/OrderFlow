package timnekk.orderflow.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import timnekk.orderflow.order.dto.CreateOrderRequest;
import timnekk.orderflow.order.entity.Order;
import timnekk.orderflow.order.dto.OrderDTO;

@Mapper(uses = {OrderDishMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "dishes", source = "orderDishes")
    OrderDTO orderToOrderDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "orderDishes", ignore = true)
    Order createOrderRequestToOrder(CreateOrderRequest createOrderRequest);

}
