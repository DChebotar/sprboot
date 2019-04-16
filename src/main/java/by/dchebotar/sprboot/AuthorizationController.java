package by.dchebotar.sprboot;

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

    /*@PostMapping("/login")
    public String login(@RequestParam(name = "name", required = false, defaultValue = "") String name, Model model){
        model.addAttribute("name", name);
        return "/login";
    }*/

}
