package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
    public String saveProfile(@RequestParam String passwordConfirm,
                              @RequestParam String password,
                              @RequestParam String mail,
                              @AuthenticationPrincipal User user,
                              Model model){
        if (!StringUtils.isEmpty(password) && !password.equals(passwordConfirm) && !StringUtils.isEmpty(passwordConfirm)){
            model.addAttribute("passwordError", "Passwords are different");
            model.addAttribute("user", user);
            return "profile";
        }
        if (!StringUtils.isEmpty(password)){
            userService.updateUser(user, mail, passwordConfirm);
            return "redirect:/profile";
        }
        User userFromDB = userService.getUserByUsername(user.getUsername());
        userService.updateUser(user, mail, userFromDB.getPassword());
        return "redirect:/profile";
    }
}
