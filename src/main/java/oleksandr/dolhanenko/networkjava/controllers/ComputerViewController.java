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
@RequestMapping("/view")
public class ComputerViewController {
    @Autowired
    private ComputerRepository computerRepository;

    @GetMapping("/computers")
    public String messages(Model model) {
        model.addAttribute("computers", computerRepository.findAll());
        return "computers";
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

//    @PostMapping("/save")
//    public String saveComputer(Computer computer) {
//        if (computer != null) {
//            computerRepository.save(computer);
//        }
//        return "redirect:http://localhost:8080/test/computers";
//    }
}
