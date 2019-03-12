package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.utils.ComputerGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public Computer getComputerById(int id) {
        Computer foundComputer = null;
        for (Computer computer : allComputers) {
            if (computer.getId() == id) {
                foundComputer = computer;
            }
        }
        return foundComputer;
    }

}
