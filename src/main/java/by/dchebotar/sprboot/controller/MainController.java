package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String main(Model model){
        return "main";
    }

    /*@GetMapping("/login")
    public String login(){
        return "login";
    }

    /*@PostMapping
    public String login(@RequestParam String username, String password){
        User user = userRepository.findByName(username);
        if (user == null) return "login";
        else {
        if (user.getPassword().equals(password)){
            return "work";
        }
        else return "login";
        }
    }

    @GetMapping("/work")
    public String work(String name, Model model){
        //model.addAttribute("name", userRepository);
        return "work";
    }*/

}
