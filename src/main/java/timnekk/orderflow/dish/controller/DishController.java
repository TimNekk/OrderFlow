package timnekk.orderflow.dish.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import timnekk.orderflow.exception.dto.StringExceptionResponse;
import timnekk.orderflow.exception.dto.StringMapExceptionResponse;

@RestController
@RequestMapping("/api/v1/dish")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @Operation(summary = "Get dish by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved dish",
                    content = @Content(schema = @Schema(implementation = DishDTO.class))),
            @ApiResponse(responseCode = "404", description = "Dish not found",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is not authenticated",
                    content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Integer id) {
        Dish dish = dishService.getDishById(id);
        DishDTO dishDTO = DishMapper.INSTANCE.dishToDishDTO(dish);

        return ResponseEntity.status(HttpStatus.OK).body(dishDTO);
    }

    @Operation(summary = "Create new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new dish",
                    content = @Content(schema = @Schema(implementation = DishDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is not authenticated",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @PostMapping
    public ResponseEntity<DishDTO> createDish(@RequestBody @Valid CreateDishRequest createDishRequest) {
        Dish dish = DishMapper.INSTANCE.createDishRequestToDish(createDishRequest);
        Dish createdDish = dishService.createDish(dish);
        DishDTO createdDishDTO = DishMapper.INSTANCE.dishToDishDTO(createdDish);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDishDTO);
    }

    @Operation(summary = "Update existing dish by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated dish",
                    content = @Content(schema = @Schema(implementation = DishDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dish not found",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is not authenticated",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Integer id, @RequestBody @Valid UpdateDishRequest updateDishRequest) {
        Dish dish = dishService.getDishById(id);
        DishMapper.INSTANCE.updateDishFromRequest(updateDishRequest, dish);
        Dish updatedDish = dishService.updateDish(dish);
        DishDTO updatedDishDTO = DishMapper.INSTANCE.dishToDishDTO(updatedDish);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDishDTO);
    }

    @Operation(summary = "Delete existing dish by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted dish"),
            @ApiResponse(responseCode = "404", description = "Dish not found",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is not authenticated",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Integer id) {
        dishService.deleteDish(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}