package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.utils.IComputersDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computers")
public class ComputersController {
    @Autowired
    private IComputersDataService dataService;

    @GetMapping
    public ResponseEntity<List<Computer>> allComputers() {
        return new ResponseEntity<>(dataService.getAllComputers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Computer> getComputer(@PathVariable int id) {
        Computer foundComputer = dataService.getComputer(id);
        if (foundComputer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundComputer, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addComputer(@RequestBody Computer computer) {
        if (computer != null) {
            dataService.addComputer(computer);
            return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to add, bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeComputer(@RequestParam int id) {
        boolean removed = dataService.removeComputer(id);
        if (removed) {
            return new ResponseEntity<>("Successfully removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No such computer with id: " + id, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateComputer(@RequestBody Computer computer) {
        if (computer != null) {
            boolean updated = dataService.updateComputer(computer);
            if (updated) {
                return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No such computer with id: " + computer.getId(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Empty request body", HttpStatus.BAD_REQUEST);
        }
    }
}
