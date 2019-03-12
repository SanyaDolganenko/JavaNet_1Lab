package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.utils.ComputerGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computers")
public class ComputersController {
    private List<Computer> allComputers = ComputerGenerator.generate(5);

    @GetMapping
    public ResponseEntity<List<Computer>> allComputers() {
        return new ResponseEntity<>(allComputers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Computer> getComputer(@PathVariable int id) {
        Computer foundComputer = getComputerById(id);
        if (foundComputer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundComputer, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addComputer(@RequestBody Computer computer) {
        if (computer != null) {
            computer.setId(getMaxComputerId());
            if (computer.getCpu() == null) {
                computer.setCpu("none");
            }
            if (computer.getGpu() == null) {
                computer.setGpu("none");
            }
            allComputers.add(computer);
            return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to add, bad request", HttpStatus.BAD_REQUEST);
        }
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
