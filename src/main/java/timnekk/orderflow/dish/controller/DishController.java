package timnekk.orderflow.dish.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timnekk.orderflow.dish.dto.CreateDishRequest;
import timnekk.orderflow.dish.dto.DishDTO;
import timnekk.orderflow.dish.dto.UpdateDishRequest;
import timnekk.orderflow.dish.entity.Dish;
import timnekk.orderflow.dish.mapper.DishMapper;
import timnekk.orderflow.dish.service.DishService;

@RestController
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Integer id) {
        Dish dish = dishService.getDishById(id);
        DishDTO dishDTO = DishMapper.INSTANCE.dishToDishDTO(dish);

        return ResponseEntity.status(HttpStatus.OK).body(dishDTO);
    }

    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody @Valid CreateDishRequest createDishRequest) {
        Dish dish = DishMapper.INSTANCE.createDishRequestToDish(createDishRequest);
        Dish createdDish = dishService.createDish(dish);
        DishDTO createdDishDTO = DishMapper.INSTANCE.dishToDishDTO(createdDish);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDishDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Integer id, @RequestBody @Valid UpdateDishRequest updateDishRequest) {
        Dish dish = dishService.getDishById(id);
        DishMapper.INSTANCE.updateDishFromRequest(updateDishRequest, dish);
        Dish updatedDish = dishService.updateDish(dish);
        DishDTO updatedDishDTO = DishMapper.INSTANCE.dishToDishDTO(updatedDish);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDishDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Integer id) {
        dishService.deleteDish(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}