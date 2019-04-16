package by.dchebotar.sprboot;

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
public class AuthorizationController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String main(){
        return "/main";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping
    public String login(@RequestParam String username, String password){
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        System.out.println(username + " " + password);
        return "/login";
    }

}
