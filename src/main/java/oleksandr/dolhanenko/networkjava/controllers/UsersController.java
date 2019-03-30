package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.model.User;
import oleksandr.dolhanenko.networkjava.model.User;
import oleksandr.dolhanenko.networkjava.utils.ComputerRepository;
import oleksandr.dolhanenko.networkjava.utils.UserRepository;
import oleksandr.dolhanenko.networkjava.utils.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComputerRepository computerRepository;


    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User foundUser = userRepository.getOne(id);
        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (user != null) {
            userRepository.save(user);
            return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to add, bad request", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeUser(@RequestParam int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return new ResponseEntity<>("Successfully removed", HttpStatus.OK);
        }
        return new ResponseEntity<>("No such user with id: " + id, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        if (user != null) {
            if (userRepository.findById(user.getId()).isPresent()) {
                userRepository.save(user);
                return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No such user :(", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Empty request body", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/start_using_computer")
    public ResponseEntity<String> startBeingUsedBy(@PathVariable int userId, @RequestParam int computerId) {
        if (userRepository.findById(userId).isPresent()) {
            if (computerRepository.findById(computerId).isPresent()) {
                Computer computer = computerRepository.getOne(computerId);
                User user = userRepository.getOne(userId);
                computer.getUsers().add(user);
                user.getComputers().add(computer);
                computerRepository.save(computer);
                userRepository.save(user);
                return new ResponseEntity<>("Successfully registered connection", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No such computer.", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("No such user.", HttpStatus.BAD_REQUEST);
    }
}