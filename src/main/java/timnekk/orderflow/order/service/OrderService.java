package timnekk.orderflow.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import timnekk.orderflow.exception.OrderNotFoundException;
import timnekk.orderflow.order.entity.Order;
import timnekk.orderflow.order.entity.OrderStatus;
import timnekk.orderflow.order.repository.OrderRepository;
import timnekk.orderflow.user.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));

        log.info("Order with id {} was found 1", id);
        if (!order.getUser().getId().equals(userService.getCurrentUser().getId())) {
            throw new OrderNotFoundException("Order with id " + id + " not found");
        }
        log.info("Order with id {} was found 2", id);

        return order;
    }
}
