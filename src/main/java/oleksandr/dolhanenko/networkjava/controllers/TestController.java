package oleksandr.dolhanenko.networkjava.controllers;

import oleksandr.dolhanenko.networkjava.utils.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
