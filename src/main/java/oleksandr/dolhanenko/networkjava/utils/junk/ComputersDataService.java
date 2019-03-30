package oleksandr.dolhanenko.networkjava.utils.junk;

import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.utils.ComputerGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("DataServiceImplementation")
public class ComputersDataService implements IComputersDataService {
    private List<Computer> allComputers;

    public ComputersDataService() {
        allComputers = ComputerGenerator.generate(5);
    }

    @Override
    public List<Computer> getAllComputers() {
        return allComputers;
    }

    @Override
    public Computer getComputer(int id) {
        return getComputerById(id);
    }

    @Override
    public boolean addComputer(Computer computer) {
        computer.setId(getMaxComputerId() + 1);
        if (computer.getCpu() == null) {
            computer.setCpu("none");
        }
        if (computer.getGpu() == null) {
            computer.setGpu("none");
        }
        allComputers.add(computer);
        return true;
    }

    @Override
    public boolean updateComputer(Computer computer) {
        Computer computerByRequestId = getComputerById(computer.getId());
        if (computerByRequestId != null) {
            computerByRequestId.setGpu(computer.getGpu());
            computerByRequestId.setCpu(computer.getCpu());
            computerByRequestId.setRamSize(computer.getRamSize());
        }
        return computerByRequestId != null;
    }

    @Override
    public boolean removeComputer(int id) {
        Computer toRemove = getComputerById(id);
        if (toRemove != null) {
            allComputers.remove(toRemove);
        }
        return toRemove != null;
    }

    private Computer getComputerById(int id) {
        Computer foundComputer = null;
        for (Computer computer : allComputers) {
            if (computer.getId() == id) {
                foundComputer = computer;
            }
        }
        return foundComputer;
    }

    private int getMaxComputerId() {
        int maxId = -1;
        for (Computer computer : allComputers) {
            if (computer.getId() > maxId) {
                maxId = computer.getId();
            }
        }
        return maxId;
    }
}
