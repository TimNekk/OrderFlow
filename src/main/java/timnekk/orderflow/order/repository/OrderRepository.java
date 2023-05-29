package timnekk.orderflow.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timnekk.orderflow.order.entity.Order;
import timnekk.orderflow.order.entity.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

}
