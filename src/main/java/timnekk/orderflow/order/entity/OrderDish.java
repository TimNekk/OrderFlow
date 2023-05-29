package timnekk.orderflow.order.entity;

import jakarta.persistence.*;
import lombok.*;
import timnekk.orderflow.dish.entity.Dish;
import timnekk.orderflow.misc.BaseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "order_dish")
@IdClass(OrderDishId.class)
public class OrderDish extends BaseEntity {

    @Id
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name="dish_id", nullable=false)
    private Dish dish;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float price;

}