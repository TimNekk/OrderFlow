package timnekk.orderflow.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import timnekk.orderflow.dish.repository.DishRepository;
import timnekk.orderflow.exception.OrderNotFoundException;
import timnekk.orderflow.order.entity.Order;
import timnekk.orderflow.order.entity.OrderStatus;
import timnekk.orderflow.order.repository.OrderRepository;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderProcessor {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    @RabbitListener(queues = "order-queue")
    public void processOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " not found"));

        // Check if there are enough dishes in stock
        // If not, set order status to CANCELLED
        // If yes, reduce the number of dishes in stock

        order.setStatus(OrderStatus.IN_PROGRESS);
        orderRepository.save(order);

        // Fake Processing...
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }

}