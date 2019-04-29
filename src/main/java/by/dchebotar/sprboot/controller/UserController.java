package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.Role;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;
import java.util.Set;


@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String saveProfile(@AuthenticationPrincipal User user,
                              @RequestParam String mail,
                              BindingResult bindingResult,
                              Model model){
        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())){
            model.addAttribute("passwordError", "Passwords are different");
            return "profile";
        }
        if (bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "profile";
        }
        userService.updateUser(user, mail);
        return "redirect:/profile";
    }
}
