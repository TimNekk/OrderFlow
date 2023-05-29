package timnekk.orderflow.dish.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {

    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Integer quantity;

}
