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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timnekk.orderflow.dish.mapper.DishMapper;
import timnekk.orderflow.dish.service.DishService;
import timnekk.orderflow.dish.entity.Dish;
import timnekk.orderflow.dish.dto.DishDTO;
import timnekk.orderflow.dish.dto.DishesRequest;
import timnekk.orderflow.exception.dto.StringExceptionResponse;
import timnekk.orderflow.exception.dto.StringMapExceptionResponse;

@RestController
@RequestMapping("/api/v1/dishes")
@RequiredArgsConstructor
public class DishesController {

    private final DishService dishService;

    @Operation(summary = "Get dishes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved dishes",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class))),
        @ApiResponse(responseCode = "403", description = "User is not authenticated",
                content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<DishDTO>> getDishes(@Valid DishesRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getPageSize());
        Page<Dish> dishes = dishService.getDishes(pageable);
        Page<DishDTO> dishDTOs = dishes.map(DishMapper.INSTANCE::dishToDishDTO);

        return ResponseEntity.status(HttpStatus.OK).body(dishDTOs);
    }

}
