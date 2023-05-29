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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Column
    private String specialRequests;

    @OneToMany(mappedBy = "order")
    private Set<OrderDish> orderDishes;

}