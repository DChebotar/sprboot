package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String admin(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/filterByName")
    public String filterByName(@RequestParam String username, Model model){
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "admin";
    }

    @PostMapping("/filterByStatus")
    public String filterByActiv(Model model){
        Iterable<User> users = userRepository.findUsersByActive(true);
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/allusers")
    public String seeAll(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }
}
