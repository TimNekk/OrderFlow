package timnekk.orderflow.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import timnekk.orderflow.order.entity.OrderDish;
import timnekk.orderflow.order.entity.OrderDishId;

public interface OrderDishRepository extends JpaRepository<OrderDish, OrderDishId> {
}
