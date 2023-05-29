package timnekk.orderflow.dish.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timnekk.orderflow.dish.mapper.DishMapper;
import timnekk.orderflow.dish.service.DishService;
import timnekk.orderflow.dish.entity.Dish;
import timnekk.orderflow.dish.dto.DishDTO;
import timnekk.orderflow.dish.dto.DishesRequest;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishesController {

    private final DishService dishService;

    @GetMapping
    public ResponseEntity<Page<DishDTO>> getDishes(@Valid DishesRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        Page<Dish> dishes = dishService.getDishes(pageable);
        Page<DishDTO> dishDTOs = dishes.map(DishMapper.INSTANCE::dishToDishDTO);

        return ResponseEntity.status(HttpStatus.OK).body(dishDTOs);
    }

}
