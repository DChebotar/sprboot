package by.dchebotar.sprboot.controller;

import by.dchebotar.sprboot.domain.Role;
import by.dchebotar.sprboot.domain.User;
import by.dchebotar.sprboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String admin(Model model){
        Iterable<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/filterByName")
    public String filterByName(@RequestParam String username, Model model){
        Set<User> users = userService.getListOfUsersByUsername(username);
        if (!users.isEmpty()){
            model.addAttribute("users", users);
            return "admin";
        }
        model.addAttribute("message", "User is not found!");
        return "admin";
    }

    @PostMapping("/filterByStatus")
    public String filterByActiv(Model model){
        Iterable<User> users = userService.getActiveUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/allusers")
    public String seeAll(Model model){
        Iterable<User> users = userService.getAllUsers();
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
                           @RequestParam String active,
                           @RequestParam Map<String, String> form,
                           @RequestParam("id") User user,
                           Model model){
        Set<Role> roles = Arrays.stream(Role.values()).collect(Collectors.toSet());
        for (Role role : roles) {
            if (form.containsKey(role.toString())){
                user.getRoles().add(role);
            }
        }
        userService.saveUser(user, username, passrord, mail, Boolean.valueOf(active), roles);
        return "redirect:/admin";
    }

    @PostMapping("/useredit/delete")
    public String deleteUser(@RequestParam("id") User user){
        userService.deleteUser(user);
        return "redirect:/admin";
    }

}
