package timnekk.orderflow.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timnekk.orderflow.dish.service.DishService;
import timnekk.orderflow.exception.DishNotFoundException;
import timnekk.orderflow.exception.dto.StringExceptionResponse;
import timnekk.orderflow.exception.dto.StringMapExceptionResponse;
import timnekk.orderflow.order.dto.CreateOrderRequest;
import timnekk.orderflow.order.dto.OrderDTO;
import timnekk.orderflow.order.entity.Order;
import timnekk.orderflow.order.entity.OrderDish;
import timnekk.orderflow.order.mapper.OrderDishMapper;
import timnekk.orderflow.order.service.OrderDishService;
import timnekk.orderflow.order.mapper.OrderMapper;
import timnekk.orderflow.order.service.OrderService;
import timnekk.orderflow.user.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final DishService dishService;
    private final OrderDishService orderDishService;
    private final RabbitTemplate rabbitTemplate;


    @Operation(summary = "Create new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created new order",
                    content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = StringMapExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Dish not found",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is not authenticated",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Order order = OrderMapper.INSTANCE.createOrderRequestToOrder(createOrderRequest);
        order.setUser(userService.getCurrentUser());
        Order createdOrder = orderService.createOrder(order);

        Set<OrderDish> orderDishes = OrderDishMapper.INSTANCE.orderDishRequestsToOrderDishes(createOrderRequest.getDishes());
        try {
            orderDishes.forEach(orderDish -> {
                orderDish.setOrder(createdOrder);
                orderDish.setDish(dishService.getDishById(orderDish.getDish().getId()));
                orderDish.setPrice(orderDish.getDish().getPrice());
                orderDishService.createOrderDish(orderDish);
            });
        } catch (DishNotFoundException e) {
            orderService.deleteOrder(createdOrder.getId());
            throw e;
        }

        rabbitTemplate.convertAndSend("order-exchange", "order.created", order.getId());
        createdOrder.setOrderDishes(orderDishes);

        OrderDTO orderDTO = OrderMapper.INSTANCE.orderToOrderDTO(createdOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @Operation(summary = "Get order by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved order",
                    content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class))),
            @ApiResponse(responseCode = "403", description = "User is not authenticated",
                    content = @Content(schema = @Schema(implementation = StringExceptionResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        OrderDTO orderDTO = OrderMapper.INSTANCE.orderToOrderDTO(order);

        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

}
