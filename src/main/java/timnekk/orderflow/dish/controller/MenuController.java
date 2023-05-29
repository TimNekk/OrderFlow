package timnekk.orderflow.dish.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timnekk.orderflow.dish.mapper.DishMapper;
import timnekk.orderflow.dish.service.DishService;
import timnekk.orderflow.dish.entity.Dish;
import timnekk.orderflow.dish.dto.DishDTO;
import timnekk.orderflow.dish.dto.MenuRequest;

@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController {

    private final DishService dishService;

    @GetMapping
    public ResponseEntity<Page<DishDTO>> getAvailableDishes(@Valid MenuRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        Page<Dish> dishes = dishService.getAvailableDishes(pageable);
        Page<DishDTO> dishDTOs = dishes.map(DishMapper.INSTANCE::dishToDishDTO);

        return ResponseEntity.status(HttpStatus.OK).body(dishDTOs);
    }

}