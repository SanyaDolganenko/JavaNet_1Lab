package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.model.User;
import oleksandr.dolhanenko.networkjava.utils.ComputerRepository;
import oleksandr.dolhanenko.networkjava.utils.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
    @Autowired
    private ComputerRepository computerRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/computers")
    public String messages(Model model) {
        model.addAttribute("computers", computerRepository.findAll());
        return "computers";
    }

    @GetMapping("/computer-users")
    public String computerUsers(Model model) {
        model.addAttribute("computers", computerRepository.findAll());
        return "computer-users";
    }

    @GetMapping("/computers/edit/{id}")
    public String greeting(@PathVariable("id") int id, Model model) {
        model.addAttribute("computer", computerRepository.getOne(id));
        return "computer-edit";
    }

    @PostMapping("/computers/update")
    public String updateComputer(Computer computer) {
        if (computer != null) {
            computerRepository.save(computer);
        }
        return "redirect:http://localhost:8080/view/computers";
    }

    @GetMapping("/computers/remove/{id}")
    public String removeComputer(@PathVariable("id") int id) {
        if (computerRepository.findById(id).isPresent()) {
            computerRepository.deleteById(id);

        }
        return "redirect:http://localhost:8080/view/computers";
    }

    @GetMapping("/computers/add")
    public String addComputer(Model model) {
        model.addAttribute("computer", new Computer());
        return "computer-new";
    }


    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userRepository.getOne(id));
        return "user-edit";
    }

    @PostMapping("/users/update")
    public String updateUser(User user) {
        if (user != null) {
            userRepository.save(user);
        }
        return "redirect:http://localhost:8080/view/users";
    }

    @GetMapping("/users/remove/{id}")
    public String removeUser(@PathVariable("id") int id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
        return "redirect:http://localhost:8080/view/users";
    }

    @GetMapping("/users/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user-new";
    }


    @GetMapping("/sockets")
    public String sockets() {
        return "sockets";
    }


}
