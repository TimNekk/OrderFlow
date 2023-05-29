package timnekk.orderflow.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import timnekk.orderflow.order.entity.OrderDish;
import timnekk.orderflow.order.dto.OrderDishRequest;
import timnekk.orderflow.order.dto.OrderDishResponse;

import java.util.Set;

@Mapper
public interface OrderDishMapper {

    OrderDishMapper INSTANCE = Mappers.getMapper(OrderDishMapper.class);

    @Mapping(target = "id", source = "dish.id")
    OrderDishRequest orderDishToOrderDishDTO(OrderDish orderDish);

    Set<OrderDishRequest> orderDishesToOrderDishDTOs(Set<OrderDish> orderDishes);

    @Mapping(target = "id", source = "dish.id")
    @Mapping(target = "name", source = "dish.name")
    OrderDishResponse orderDishToOrderDishResponse(OrderDish orderDish);

    Set<OrderDishResponse> orderDishesToOrderDishResponses(Set<OrderDish> orderDishes);

    @Mapping(target = "dish.id", source = "id")
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "price", ignore = true)
    OrderDish orderDishDTOToOrderDish(OrderDishRequest orderDishRequest);

    Set<OrderDish> orderDishRequestsToOrderDishes(Set<OrderDishRequest> orderDishRequests);

}
