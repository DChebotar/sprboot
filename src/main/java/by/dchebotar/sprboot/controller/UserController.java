package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String saveProfile(@AuthenticationPrincipal User user,
                              @RequestParam String mail,
                              @RequestParam String password,
                              Model model){
        userService.saveUser(user, user.getUsername(), password, mail == null ? "" : mail, user.isActive(), user.getRoles());
        return "profile";
    }
}
