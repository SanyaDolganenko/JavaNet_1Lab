package oleksandr.dolhanenko.networkjava.utils;

import oleksandr.dolhanenko.networkjava.model.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerRepository extends JpaRepository<Computer, Integer> {
}
