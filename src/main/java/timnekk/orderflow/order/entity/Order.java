package timnekk.orderflow.order.entity;

import jakarta.persistence.*;
import lombok.*;
import timnekk.orderflow.misc.BaseEntity;
import timnekk.orderflow.user.entity.User;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column
    private String specialRequests;

    @OneToMany(mappedBy = "order")
    private Set<OrderDish> orderDishes;

}