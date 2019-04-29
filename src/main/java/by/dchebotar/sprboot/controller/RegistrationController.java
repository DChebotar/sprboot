package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid User user, BindingResult bindingResult, Model model){
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())){
            model.addAttribute("passwordError", "Passwords are different");
            return "registration";
        }
        if (bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (userService.addUser(user) == false) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
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
