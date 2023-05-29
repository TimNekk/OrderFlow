package timnekk.orderflow.dish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import timnekk.orderflow.dish.entity.Dish;
import timnekk.orderflow.dish.repository.DishRepository;
import timnekk.orderflow.exception.DishNotFoundException;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public Page<Dish> getAvailableDishes(Pageable pageable) {
        return dishRepository.findAllByQuantityGreaterThan(0, pageable);
    }

    public Page<Dish> getDishes(Pageable pageable) {
        return dishRepository.findAll(pageable);
    }

    public Dish createDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish getDishById(Integer id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException("Dish with id " + id + " not found"));
    }

    public void deleteDish(Integer id) {
        dishRepository.deleteById(id);
    }

    public Dish updateDish(Dish dish) {
        return dishRepository.save(dish);
    }

}