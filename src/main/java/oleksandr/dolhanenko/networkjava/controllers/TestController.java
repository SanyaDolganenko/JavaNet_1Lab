package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.model.Computer;
import oleksandr.dolhanenko.networkjava.utils.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ComputerRepository computerRepository;

    @GetMapping("/computers")
    public String messages(Model model) {
        model.addAttribute("computers", computerRepository.findAll());
        return "computers";
    }

    @GetMapping("/edit/{id}")
    public String greeting(@PathVariable("id") int id, Model model) {
        model.addAttribute("computer", computerRepository.getOne(id));
        return "computer-edit";
    }

    @PostMapping("/update")
    public String updateComputer(Computer computer) {
        if (computer != null) {
            if (computerRepository.findById(computer.getId()).isPresent()) {
                computerRepository.save(computer);
            }
        }
        return "redirect:computers";
    }

    @GetMapping("/remove/{id}")
    public String removeComputer(@PathVariable("id") int id) {
        if (computerRepository.findById(id).isPresent()) {
            computerRepository.deleteById(id);

        }
        return "redirect:http://localhost:8080/test/computers";
    }
}
