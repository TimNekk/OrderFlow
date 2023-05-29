package timnekk.orderflow.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import timnekk.orderflow.order.entity.OrderDish;
import timnekk.orderflow.order.repository.OrderDishRepository;

@Service
@RequiredArgsConstructor
public class OrderDishService {

    private final OrderDishRepository orderDishRepository;

    public OrderDish createOrderDish(OrderDish orderDish) {
        return orderDishRepository.save(orderDish);
    }

}
