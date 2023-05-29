package timnekk.orderflow.dish.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import timnekk.orderflow.exception.dto.StringExceptionResponse;
import timnekk.orderflow.exception.dto.StringMapExceptionResponse;

@RestController
@RequestMapping("/api/v1/menu")
@RequiredArgsConstructor
public class MenuController {

    private final DishService dishService;
@Operation(summary = "Get available dishes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved dishes",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class))),
        @ApiResponse(responseCode = "403", description = "User is not authenticated",
                content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<DishDTO>> getAvailableDishes(@Valid MenuRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        Page<Dish> dishes = dishService.getAvailableDishes(pageable);
        Page<DishDTO> dishDTOs = dishes.map(DishMapper.INSTANCE::dishToDishDTO);

        return ResponseEntity.status(HttpStatus.OK).body(dishDTOs);
    }

}