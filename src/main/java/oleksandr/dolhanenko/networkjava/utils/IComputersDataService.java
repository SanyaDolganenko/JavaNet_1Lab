package oleksandr.dolhanenko.networkjava.utils;

import oleksandr.dolhanenko.networkjava.model.Computer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service("ComputerDataServiceInterface")
public interface IComputersDataService {

    List<Computer> getAllComputers();

    Computer getComputer(int id);

    boolean addComputer(Computer computer);

    boolean updateComputer(Computer computer);

    boolean removeComputer(int id);

}
