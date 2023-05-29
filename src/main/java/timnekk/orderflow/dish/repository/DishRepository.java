package timnekk.orderflow.dish.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import timnekk.orderflow.dish.entity.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    Page<Dish> findAllByQuantityGreaterThan(Integer quantity, Pageable pageable);

}
