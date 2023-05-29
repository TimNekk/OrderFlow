package timnekk.orderflow.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import timnekk.orderflow.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}