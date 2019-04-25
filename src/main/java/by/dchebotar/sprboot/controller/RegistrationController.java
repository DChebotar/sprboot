package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, Model model){
        if (userService.addUser(user) == false){
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        model.addAttribute("message", "Сonfirm registration by following the link in your mail");
        return "redirect:/login";
    }

    @GetMapping("/activate/{activationcode}")
    public String activation(@PathVariable String activationcode, Model model){
        boolean isActivated = userService.activateUser(activationcode);
        if (isActivated) {
            model.addAttribute("message", "User succesessfully activated!");
        }
        else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }


}
