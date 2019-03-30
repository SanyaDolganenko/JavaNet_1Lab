package oleksandr.dolhanenko.networkjava.utils;

import oleksandr.dolhanenko.networkjava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
