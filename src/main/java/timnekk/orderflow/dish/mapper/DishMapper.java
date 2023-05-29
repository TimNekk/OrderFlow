package timnekk.orderflow.dish.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import timnekk.orderflow.dish.dto.CreateDishRequest;
import timnekk.orderflow.dish.entity.Dish;
import timnekk.orderflow.dish.dto.DishDTO;
import timnekk.orderflow.dish.dto.UpdateDishRequest;

@Mapper
public interface DishMapper {

    DishMapper INSTANCE = Mappers.getMapper(DishMapper.class);

    DishDTO dishToDishDTO(Dish dish);

    @Mapping(target = "id", ignore = true)
    Dish createDishRequestToDish(CreateDishRequest createDishRequest);

    @Mapping(target = "id", ignore = true)
    void updateDishFromRequest(UpdateDishRequest updateDishRequest, @MappingTarget Dish dish);

}
