package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.Role;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String admin(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/filterByName")
    public String filterByName(@RequestParam String username, Model model){
        User user = userRepository.findByUsername(username);
        Set<User> userList = new HashSet<>();
        if (user !=null){
        userList.add(user);
        model.addAttribute("users", userList);
        return "admin";
        }
        model.addAttribute("message", "User is not found!");
        return "admin";
    }

    @PostMapping("/filterByStatus")
    public String filterByActiv(Model model){
        Iterable<User> users = userRepository.findByActive(true);
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/allusers")
    public String seeAll(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping("/useredit/{user}")
    public String userEdit(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "useredit";
    }

    @PostMapping("/useredit")
    public String saveEdit(@RequestParam String username,
                           @RequestParam String mail,
                           @RequestParam String passrord,
                           @RequestParam Map<String, String> form,
                           @RequestParam("id") User user,
                           Model model){
        user.setUsername(username);
        user.setMail(mail);
        user.setPassword(passrord);
        user.getRoles().clear();

        Set<Role> roles = Arrays.stream(Role.values()).collect(Collectors.toSet());
        for (Role role : roles) {
            if (form.containsKey(role.toString())){
                user.getRoles().add(role);
            }
        }
        userRepository.save(user);
        return "redirect:/admin";
    }
}
