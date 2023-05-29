package timnekk.orderflow.dish.entity;

import jakarta.persistence.*;
import lombok.*;
import timnekk.orderflow.misc.BaseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "dishes")
public class Dish extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dishes_id_seq")
    @SequenceGenerator(name = "dishes_id_seq", sequenceName = "dishes_id_seq", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Integer quantity;

}
