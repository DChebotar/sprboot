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
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registration(@RequestParam String passwordConfirm, @Valid User user, BindingResult bindingResult, Model model){
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty){
            model.addAttribute("password2Error", "Password confirmation cannot be empty");

        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("passwordError", "Passwords are different");

        }
        if (isConfirmEmpty || bindingResult.hasErrors()){
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
            model.addAttribute("messageSuccess", "User successfully activated!");
        }
        else {
            model.addAttribute("messageDanger", "Activation code is not found");
        }
        return "login";
    }
}
